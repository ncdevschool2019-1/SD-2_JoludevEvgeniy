import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../../services/modal.service';
import {BillingAccountService} from '../../../../services/billingAccount.service';
import {BillingAccount} from '../../../models/billing-account';
import {AuthorizationService} from '../../../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {User} from '../../../models/user';

@Component({
  selector: 'app-add-billing-account',
  templateUrl: './add-billing-account.component.html'
})

export class AddBillingAccountComponent implements OnInit, OnDestroy {

  selectedBillingAccount: BillingAccount = new BillingAccount();
  private subscriptions: Subscription[] = [];
  authorizedUser: User = new User();

  constructor(private modalService: ModalService, private billingAccountService: BillingAccountService,
              private authService: AuthorizationService, private toastr: ToastrService) {

  }

  ngOnInit(): void {
    this.getAuthUser();
  }

  getAuthUser() {
    this.subscriptions.push(this.authService.subscribeToAuthUser().subscribe(value => {
      this.authorizedUser = value;
    }));
    this.authService.getAuthUser();

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  closeModal() {
    this.modalService.closeModal();
    this.selectedBillingAccount = new BillingAccount();
  }

  addBillingAccount(billingAccount: BillingAccount) {
    billingAccount.userId = this.authorizedUser.id;
    this.subscriptions.push(this.billingAccountService.saveBillingAccount(billingAccount).subscribe(data => {
      this.authorizedUser.billingAccounts.push(data);
      this.authService.setAuthUser(this.authorizedUser);
      this.closeModal();
      this.toastr.success('Вам удалось создать биллинг аккаунт!', billingAccount.name);
    }, error => {
      this.toastr.error('Создание биллинг аккаунта не удалось', 'Ошибка');
    }));
  }

}
