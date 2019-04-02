import {Component, Input, OnInit} from '@angular/core';
import {BillingAccountService} from "../../../../services/billingAccountService/billingAccount.service";
import {AuthorizationService} from "../../../../services/authorizationService/authorization.service";
import {ModalService} from "../../../../services/modalService/modal.service";
import {SubscriptService} from '../../../../services/subscriptService/subscript.service';
import {Subscript} from '../../../models/subscript';
import {ActiveSubscript} from '../../../models/active-subscript';
import {DatePipe} from '@angular/common';
import {ActiveSubscriptService} from '../../../../services/activeSubscriptService/active-subscript.service';

@Component({
  selector: 'app-to-subscript',
  templateUrl: 'to-subscript.component.html'
})

export class ToSubscriptComponent implements OnInit {


  constructor(private billingAccountService: BillingAccountService, private authService: AuthorizationService,
              private modalService: ModalService, private subscriptService: SubscriptService,
              private activeSubscriptService: ActiveSubscriptService){

  }

  ngOnInit(): void {
  }

  saveActiveSubscript(subscript: Subscript, billingAccountId: number) : void{
    let activeSubscript: ActiveSubscript = new ActiveSubscript();
    activeSubscript.subscript = Subscript.cloneSubscript(subscript);
    activeSubscript.billingAccountId = billingAccountId;
    this.activeSubscriptService.saveActiveSubscript(activeSubscript).subscribe(data => {
      this.authService.updateAuthorization();
      this.closeModal();
    });



  }

  closeModal(): void {
    this.modalService.closeModal();
    this.billingAccountService.clearSelectedBillingAccount();
    this.subscriptService.clearSelectedSubscript();
  }
}
