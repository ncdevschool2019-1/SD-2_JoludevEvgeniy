import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {BillingAccountService} from '../../../../services/billingAccount.service';
import {ModalService} from '../../../../services/modal.service';
import {ActiveSubscriptService} from '../../../../services/active-subscript.service';
import {AuthorizationService} from '../../../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {BillingAccount} from '../../../models/billing-account';
import {ActiveSubscript} from '../../../models/active-subscript';
import {Subscription} from 'rxjs';
import {User} from '../../../models/user';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';


@Component({
  selector: 'app-subscripts-table',
  templateUrl: './subscripts-table.component.html',
})
export class SubscriptsTableComponent implements OnInit, OnDestroy {

  @Input() selectedBillingAccount: BillingAccount;
  private subscriptions: Subscription[] = [];
  authorizedUser: User = new User();

  constructor(private billingAccountService: BillingAccountService, private modalService: ModalService,
              private activeSubscriptService: ActiveSubscriptService, private authService: AuthorizationService,
              private toastr: ToastrService, private loadingService: Ng4LoadingSpinnerService) {
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

  closeModal() {
    this.modalService.closeModal();
    this.selectedBillingAccount = new BillingAccount();
  }

  deleteActiveSubscript(activeSubscript: ActiveSubscript, event) {
    this.loadingService.show();
    this.subscriptions.push(this.activeSubscriptService.deleteActiveSubscript(activeSubscript.id).subscribe(data => {
      this.authorizedUser.billingAccounts.find(value =>
        value.id == this.selectedBillingAccount.id).activeSubscripts.splice(
        this.selectedBillingAccount.activeSubscripts.indexOf(activeSubscript), 1);
      this.authService.setAuthUser(this.authorizedUser);
      this.closeModal();
      this.toastr.success('Вы успешно отписались!', 'Операция удалась');
    }, error => {
      event.target.disabled = false;
      this.toastr.error('Отписаться не удалось', 'Ошибка');
    }, () => this.loadingService.hide()));

  }
}
