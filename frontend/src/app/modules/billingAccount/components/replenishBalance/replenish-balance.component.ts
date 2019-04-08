import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {BillingAccountService} from '../../../../services/billingAccount.service';
import {ModalService} from '../../../../services/modal.service';
import {BillingAccount} from '../../../models/billing-account';
import {AuthorizationService} from '../../../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';


@Component({
  selector: 'app-replenish-balance',
  templateUrl: './replenish-balance.component.html',
})
export class ReplenishBalanceComponent implements OnInit, OnDestroy {

  @Input() selectedBillingAccount: BillingAccount;

  public inputSum: number;
  private subscriptions: Subscription[] = [];

  constructor(private billingAccountService: BillingAccountService, private modalService: ModalService,
              private authService: AuthorizationService, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  public closeModal() {
    this.modalService.closeModal();
    this.selectedBillingAccount = new BillingAccount();
    this.inputSum = 0;
  }

  replenishBalance(billingAccount: BillingAccount) {
    let updatableBillingAccount = BillingAccount.cloneBillingAccount(billingAccount);
    updatableBillingAccount.balance += this.inputSum;
    this.subscriptions.push(this.billingAccountService.saveBillingAccount(updatableBillingAccount).subscribe(data => {
      this.authService.authorizedUser.billingAccounts.find(value => value.id == data.id).balance = data.balance;
      this.closeModal();
      this.toastr.success('Баланс вашего биллинг аккаунта успешно пополнен!', billingAccount.name);
    }, error => {
      this.toastr.error('Пополнить баланс не удалось', 'Операция не удалась');
    }));
  }
}
