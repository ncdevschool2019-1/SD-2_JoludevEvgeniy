import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {BillingAccountService} from '../../../../services/billingAccount.service';
import {AuthorizationService} from '../../../../services/authorization.service';
import {ModalService} from '../../../../services/modal.service';
import {SubscriptService} from '../../../../services/subscript.service';
import {Subscript} from '../../../models/subscript';
import {ActiveSubscript} from '../../../models/active-subscript';
import {ActiveSubscriptService} from '../../../../services/active-subscript.service';
import {ToastrService} from 'ngx-toastr';
import {BillingAccount} from '../../../models/billing-account';
import {User} from '../../../models/user';
import {Subscription} from 'rxjs';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';
import {UserService} from '../../../../services/user.service';

@Component({
  selector: 'app-to-subscript',
  templateUrl: 'to-subscript.component.html'
})

export class ToSubscriptComponent implements OnInit, OnDestroy {


  @Input() selectedSubscript: Subscript;
  selectedBillingAccount: BillingAccount = new BillingAccount();
  authorizedUser: User = new User();
  private subscriptions: Subscription[] = [];

  constructor(private billingAccountService: BillingAccountService, private authService: AuthorizationService,
              private modalService: ModalService, private subscriptService: SubscriptService,
              private activeSubscriptService: ActiveSubscriptService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService, private userService: UserService) {

  }

  ngOnInit(): void {
    this.getAuthUser();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  getAuthUser() {
    this.subscriptions.push(this.authService.subscribeToAuthUser().subscribe(value => {
      this.authorizedUser = value;
    }));
    this.authService.getAuthUser();
  }

  saveActiveSubscript(subscript: Subscript, billingAccount: BillingAccount, event): void {
    this.loadingService.show();
    let activeSubscript: ActiveSubscript = new ActiveSubscript();
    activeSubscript.subscript = subscript;
    activeSubscript.billingAccountId = billingAccount.id;
    this.subscriptions.push(this.activeSubscriptService.saveActiveSubscript(activeSubscript).subscribe(data => {
      this.closeModal();
      this.toastr.success('You successfully subscribe', data.subscript.name);
    }, error => {
      event.target.disabled = false;
      this.toastr.error('We are sorry for inconvenience', 'Server error');
      this.loadingService.hide();
    }, () => {
      this.subscriptions.push(this.userService.getLoggedUser(this.authorizedUser.login).subscribe( data => {
        this.authService.setAuthUser(data);
      }));
      this.loadingService.hide();
    }));


  }

  closeModal(): void {
    this.modalService.closeModal();
    this.selectedBillingAccount = new BillingAccount();
    this.selectedSubscript = new Subscript();
  }

  getSelectedBASubscriptsLength(billingAccount: BillingAccount): string {
    return this.billingAccountService.getSelectedBASubscriptsLength(billingAccount);
  }

  isSelectedBAIdUndefined(billingAccount: BillingAccount): boolean {
    return this.billingAccountService.isSelectedBAIdUndefined(billingAccount);
  }

  isBalanceMoreThanSubscribePrice(subscript: Subscript, billingAccount: BillingAccount): boolean {
    return this.billingAccountService.isBalanceMoreThanSubscribePrice(subscript, billingAccount);
  }
}
