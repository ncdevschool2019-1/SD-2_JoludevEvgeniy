import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {AuthorizationService} from '../../services/authorization.service';
import {BillingAccount} from '../models/billing-account';
import {BillingAccountService} from '../../services/billingAccount.service';
import {ModalService} from '../../services/modal.service';
import {ToastrService} from 'ngx-toastr';
import {User} from '../models/user';
import {Subscription} from 'rxjs';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';

@Component({
  selector: 'app-billing-account',
  templateUrl: './billing-account.component.html',
  styleUrls: ['./billing-account.component.css']
})
export class BillingAccountComponent implements OnInit, OnDestroy {


  selectedBillingAccount: BillingAccount = new BillingAccount();
  authorizedUser: User = new User();
  private subscriptions: Subscription[] = [];


  constructor(private authService: AuthorizationService, private billingAccountService: BillingAccountService,
              private modalService: ModalService, private toastr: ToastrService,
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

  openModal(template: TemplateRef<any>, billingAccount: BillingAccount) {
    this.modalService.openModal(template);
    this.selectedBillingAccount = billingAccount;
  }

  deleteBillingAccount(billingAccount: BillingAccount) {
    this.loadingService.show();
    this.subscriptions.push(this.billingAccountService.deleteBillingAccount(billingAccount.id).subscribe(data => {
      this.authorizedUser.billingAccounts.splice(this.authorizedUser.billingAccounts.indexOf(billingAccount), 1);
      this.authService.setAuthUser(this.authorizedUser);
      this.toastr.success('Your billing account was deleted', 'Success');
    }, error => {
      this.toastr.error('Billing account delete failed', 'Error');
      this.loadingService.hide();
    }, () => this.loadingService.hide()));
  }

  getSelectedBASubscriptsLength(billingAccount: BillingAccount): string {
    return this.billingAccountService.getSelectedBASubscriptsLength(billingAccount);
  }


}
