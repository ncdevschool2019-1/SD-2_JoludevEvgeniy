import {Component, OnInit, TemplateRef} from '@angular/core';
import {AuthorizationService} from "../../services/authorizationService/authorization.service";
import {BillingAccount} from "../models/billing-account";
import {BillingAccountService} from "../../services/billingAccountService/billingAccount.service";
import {ModalService} from "../../services/modalService/modal.service";
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-billing-account',
  templateUrl: './billing-account.component.html',
  styleUrls: ['./billing-account.component.css']
})
export class BillingAccountComponent implements OnInit {


  constructor(public authService: AuthorizationService, public billingAccountService: BillingAccountService,
              public modalService: ModalService, private toastr: ToastrService) { }

  ngOnInit() {
  }

  openModal(template: TemplateRef<any>, billingAccount: BillingAccount){
    this.modalService.openModal(template);
    this.billingAccountService.selectedBillingAccount = BillingAccount.cloneBillingAccount(billingAccount);
  }

  deleteBillingAccount(billingAccountId: number){
    this.billingAccountService.deleteBillingAccount(billingAccountId).subscribe(data => {
      this.authService.updateAuthorization();
      this.toastr.success('Ваш биллинг аккаунт успешно удален!', 'Успех');
    }, error => {
      this.toastr.error('Удалить биллинг аккаунт не удалось', 'Ошибка');
    })
  }


}
