import {Component, OnInit} from '@angular/core';
import {ModalService} from "../../../../services/modalService/modal.service";
import {BillingAccountService} from "../../../../services/billingAccountService/billingAccount.service";
import {UserService} from "../../../../services/userService/user.service";
import {BillingAccount} from '../../../models/billing-account';


@Component({
  selector: 'app-users-billing-accounts',
  templateUrl: './users-billing-accounts.component.html'
})
export class UsersBillingAccountsComponent implements OnInit {


  constructor(private modalService: ModalService, private billingAccountService: BillingAccountService,
              private userService: UserService) {
  }

  ngOnInit() {

  }

  closeModal(): void {
    this.modalService.closeModal();
    this.billingAccountService.clearSelectedBillingAccount();
    this.userService.clearSelectedUser();
  }

  changeStatus(billingAccount: BillingAccount): void{
    if(billingAccount.active){
      billingAccount.active = false;
    }
    else{
      billingAccount.active = true;
    }
    this.billingAccountService.saveBillingAccount(billingAccount).subscribe(data => {
      this.userService.getUserById(this.userService.selectedUser.id).subscribe(user => {
        this.userService.selectedUser = user;
      })
    })
  }
}
