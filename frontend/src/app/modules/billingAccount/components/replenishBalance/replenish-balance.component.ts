import {Component, OnInit} from '@angular/core';
import {BillingAccountService} from '../../../../services/billingAccountService/billingAccount.service';
import {ModalService} from '../../../../services/modalService/modal.service';
import {BillingAccount} from '../../../models/billing-account';
import {AuthorizationService} from '../../../../services/authorizationService/authorization.service';
import {ToastrService} from 'ngx-toastr';


@Component({
  selector: 'app-replenish-balance',
  templateUrl: './replenish-balance.component.html',
})
export class ReplenishBalanceComponent implements OnInit {

  public inputSum: number;

  constructor(private billingAccountService: BillingAccountService, private modalService: ModalService,
              private authService: AuthorizationService, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  public closeModal() {
    this.modalService.closeModal();
    this.billingAccountService.clearSelectedBillingAccount();
    this.inputSum = 0;
  }

  replenishBalance(billingAccount: BillingAccount) {
    let updatableBillingAccount = BillingAccount.cloneBillingAccount(billingAccount);
    updatableBillingAccount.balance += this.inputSum;
    this.billingAccountService.saveBillingAccount(updatableBillingAccount).subscribe(data => {
      this.authService.updateAuthorization();
      this.closeModal();
      this.toastr.success('Баланс вашего биллинг аккаунта успешно пополнен!', billingAccount.name);
    }, error => {
      this.toastr.error('Пополнить баланс не удалось', 'Операция не удалась');
    });
  }
}
