import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../../services/modalService/modal.service';
import {BillingAccountService} from '../../../../services/billingAccountService/billingAccount.service';
import {UserService} from '../../../../services/userService/user.service';
import {BillingAccount} from '../../../models/billing-account';
import {User} from '../../../models/user';
import {Subscription} from 'rxjs';
import {ToastrService} from 'ngx-toastr';


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
              private toastr: ToastrService) {
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

  changeStatus(): void {
    this.selectedBillingAccount.active ? this.selectedBillingAccount.active = false : this.selectedBillingAccount.active = true;
    this.subscriptions.push(this.billingAccountService.saveBillingAccount(this.selectedBillingAccount).subscribe(data => {
      this.closeModal();
      this.onChanged.emit();
      this.toastr.success('Статус успешно изменён!', data.name);
    }, error => {
      this.toastr.error('Статус изменить не удалось', 'Ошибка')
    }));
  }

  getSelectedBASubscriptsLength(): string {
    return this.billingAccountService.getSelectedBASubscriptsLength(this.selectedBillingAccount);
  }

  isSelectedBAIdUndefined(): boolean {
    return this.billingAccountService.isSelectedBAIdUndefined(this.selectedBillingAccount);
  }
}
