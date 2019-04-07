import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {BillingAccountService} from '../../../../services/billingAccountService/billingAccount.service';
import {ModalService} from '../../../../services/modalService/modal.service';
import {ActiveSubscriptService} from '../../../../services/activeSubscriptService/active-subscript.service';
import {AuthorizationService} from '../../../../services/authorizationService/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {BillingAccount} from '../../../models/billing-account';
import {ActiveSubscript} from '../../../models/active-subscript';
import {Subscription} from 'rxjs';


@Component({
  selector: 'app-subscripts-table',
  templateUrl: './subscripts-table.component.html',
})
export class SubscriptsTableComponent implements OnInit, OnDestroy {

  @Input() selectedBillingAccount: BillingAccount;
  private subscriptions: Subscription[] = [];

  constructor(private billingAccountService: BillingAccountService, private modalService: ModalService,
              private activeSubscriptService: ActiveSubscriptService, private authService: AuthorizationService,
              private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  closeModal() {
    this.modalService.closeModal();
    this.selectedBillingAccount = new BillingAccount();
  }

  deleteActiveSubscript(activeSubscript: ActiveSubscript) {
    this.subscriptions.push(this.activeSubscriptService.deleteActiveSubscript(activeSubscript.id).subscribe(data => {
      this.authService.authorizedUser.billingAccounts.find(value =>
        value.id == this.selectedBillingAccount.id).activeSubscripts.splice(
        this.selectedBillingAccount.activeSubscripts.indexOf(activeSubscript), 1);
      this.closeModal();
      this.toastr.success('Вы успешно отписались!', 'Операция удалась');
    }, error => {
      this.toastr.error('Отписаться не удалось', 'Ошибка');
    }));

  }
}
