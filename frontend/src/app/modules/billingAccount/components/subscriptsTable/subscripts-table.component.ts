import {Component, OnInit} from '@angular/core';
import {BillingAccountService} from '../../../../services/billingAccountService/billingAccount.service';
import {ModalService} from '../../../../services/modalService/modal.service';
import {ActiveSubscriptService} from '../../../../services/activeSubscriptService/active-subscript.service';
import {AuthorizationService} from '../../../../services/authorizationService/authorization.service';


@Component({
  selector: 'app-subscripts-table',
  templateUrl: './subscripts-table.component.html',
})
export class SubscriptsTableComponent implements OnInit {

  constructor(private billingAccountService: BillingAccountService, private modalService: ModalService,
              private activeSubscriptService: ActiveSubscriptService, private authService: AuthorizationService) {
  }

  ngOnInit() {
  }

  closeModal() {
    this.modalService.closeModal();
    this.billingAccountService.clearSelectedBillingAccount();
  }

  deleteActiveSubscript(activeSubscriptId: number) {
    this.activeSubscriptService.deleteActiveSubscript(activeSubscriptId).subscribe(data => {
      this.authService.updateAuthorization();
      this.closeModal();
    });

  }
}
