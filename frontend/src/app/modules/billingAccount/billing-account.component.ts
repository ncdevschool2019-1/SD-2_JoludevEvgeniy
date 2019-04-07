import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {AuthorizationService} from '../../services/authorizationService/authorization.service';
import {BillingAccount} from '../models/billing-account';
import {BillingAccountService} from '../../services/billingAccountService/billingAccount.service';
import {ModalService} from '../../services/modalService/modal.service';
import {ToastrService} from 'ngx-toastr';
import {User} from '../models/user';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-billing-account',
  templateUrl: './billing-account.component.html',
  styleUrls: ['./billing-account.component.css']
})
export class BillingAccountComponent implements OnInit, OnDestroy {


  selectedBillingAccount: BillingAccount = new BillingAccount();
  authorizedUser: User;
  private subscriptions: Subscription[] = [];


  constructor(public authService: AuthorizationService, public billingAccountService: BillingAccountService,
              public modalService: ModalService, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.getAuthUser();
  }

  getAuthUser(){
    this.authorizedUser = this.authService.authorizedUser;
  }

  onChanged(){
    this.getAuthUser();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  openModal(template: TemplateRef<any>, billingAccount: BillingAccount) {
    this.modalService.openModal(template);
    this.selectedBillingAccount = billingAccount;
  }

  deleteBillingAccount(billingAccount: BillingAccount) {
    this.subscriptions.push(this.billingAccountService.deleteBillingAccount(billingAccount.id).subscribe(data => {
      this.authService.authorizedUser.billingAccounts.splice(this.authService.authorizedUser.billingAccounts.indexOf(billingAccount), 1);
      this.getAuthUser();
      this.toastr.success('Ваш биллинг аккаунт успешно удален!', 'Успех');
    }, error => {
      this.toastr.error('Удалить биллинг аккаунт не удалось', 'Ошибка');
    }));
  }

  getSelectedBASubscriptsLength(billingAccount: BillingAccount): string{
    return this.billingAccountService.getSelectedBASubscriptsLength(billingAccount);
  }


}
