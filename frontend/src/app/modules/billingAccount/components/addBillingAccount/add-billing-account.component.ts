import {Component, OnInit} from '@angular/core';
import {ModalService} from '../../../../services/modalService/modal.service';
import {BillingAccountService} from '../../../../services/billingAccountService/billingAccount.service';
import {BillingAccount} from '../../../models/billing-account';
import {AuthorizationService} from '../../../../services/authorizationService/authorization.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-add-billing-account',
  templateUrl: './add-billing-account.component.html'
})

export class AddBillingAccountComponent implements OnInit {

  constructor(private modalService: ModalService, private billingAccountService: BillingAccountService,
              private authService: AuthorizationService, private toastr: ToastrService) {

  }

  ngOnInit(): void {
  }

  closeModal() {
    this.modalService.closeModal();
    this.billingAccountService.clearSelectedBillingAccount();

  }

  addBillingAccount(billingAccount: BillingAccount) {
    billingAccount.userId = this.authService.authorizedUser.id;
    this.billingAccountService.saveBillingAccount(billingAccount).subscribe(data => {
      this.authService.updateAuthorization();
      this.closeModal();
      this.toastr.success('Вам удалось создать биллинг аккаунт!', billingAccount.name);
    }, error => {
      this.toastr.error('Создание биллинг аккаунта не удалось', 'Ошибка');
    });
  }

}
