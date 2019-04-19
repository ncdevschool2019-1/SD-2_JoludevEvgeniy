import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {BillingAccountService} from '../../../../services/billingAccount.service';
import {ModalService} from '../../../../services/modal.service';
import {BillingAccount} from '../../../models/billing-account';
import {AuthorizationService} from '../../../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {User} from '../../../models/user';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';


@Component({
  selector: 'app-replenish-balance',
  templateUrl: './replenish-balance.component.html',
})
export class ReplenishBalanceComponent implements OnInit, OnDestroy {

  @Input() selectedBillingAccount: BillingAccount;

  public inputSum: number;
  private subscriptions: Subscription[] = [];
  authorizedUser: User = new User();

  constructor(private billingAccountService: BillingAccountService, private modalService: ModalService,
              private authService: AuthorizationService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService) {
  }

  ngOnInit() {
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

  public closeModal() {
    this.modalService.closeModal();
    this.selectedBillingAccount = new BillingAccount();
    this.inputSum = 0;
  }

  replenishBalance(billingAccount: BillingAccount, event) {
    this.loadingService.show();
    let updatableBillingAccount = BillingAccount.cloneBillingAccount(billingAccount);
    updatableBillingAccount.balance += this.inputSum;
    this.subscriptions.push(this.billingAccountService.saveBillingAccount(updatableBillingAccount).subscribe(data => {
      this.authorizedUser.billingAccounts.find(value => value.id == data.id).balance = data.balance;
      this.authService.setAuthUser(this.authorizedUser);
      this.closeModal();
      this.toastr.success('Баланс вашего биллинг аккаунта успешно пополнен!', billingAccount.name);
    }, error => {
      event.target.disabled = false;
      this.toastr.error('Пополнить баланс не удалось', 'Операция не удалась');
    }, () => this.loadingService.hide()));
  }
}
