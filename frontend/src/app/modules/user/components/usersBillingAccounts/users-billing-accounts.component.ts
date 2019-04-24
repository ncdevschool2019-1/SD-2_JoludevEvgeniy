import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../../services/modal.service';
import {BillingAccountService} from '../../../../services/billingAccount.service';
import {BillingAccount} from '../../../models/billing-account';
import {User} from '../../../models/user';
import {Subscription} from 'rxjs';
import {ToastrService} from 'ngx-toastr';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';


@Component({
  selector: 'app-users-billing-accounts',
  templateUrl: './users-billing-accounts.component.html'
})
export class UsersBillingAccountsComponent implements OnInit, OnDestroy {


  @Input() selectedUser: User;
  selectedBillingAccount: BillingAccount = new BillingAccount();
  private subscriptions: Subscription[] = [];
  @Output() onChanged = new EventEmitter();

  constructor(private modalService: ModalService, private billingAccountService: BillingAccountService,
              private toastr: ToastrService, private loadingService: Ng4LoadingSpinnerService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  closeModal(): void {
    this.modalService.closeModal();
    this.selectedBillingAccount = new BillingAccount();
    this.selectedUser = new User();
  }

  changeStatus(event): void {
    this.loadingService.show();
    this.selectedBillingAccount.active ? this.selectedBillingAccount.active = false : this.selectedBillingAccount.active = true;
    this.subscriptions.push(this.billingAccountService.saveBillingAccount(this.selectedBillingAccount).subscribe(data => {
      this.closeModal();
      this.onChanged.emit();
      this.toastr.success('Status was changed', data.name);
    }, error => {
      event.target.disabled = false;
      this.toastr.error('Status change failed', 'Error')
    }, () => this.loadingService.hide()));
  }

  getSelectedBASubscriptsLength(): string {
    return this.billingAccountService.getSelectedBASubscriptsLength(this.selectedBillingAccount);
  }

  isSelectedBAIdUndefined(): boolean {
    return this.billingAccountService.isSelectedBAIdUndefined(this.selectedBillingAccount);
  }
}
