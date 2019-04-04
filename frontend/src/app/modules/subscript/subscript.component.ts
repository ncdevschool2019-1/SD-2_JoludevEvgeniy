import {Component, OnInit, TemplateRef} from '@angular/core';
import {Subscript} from '../models/subscript';
import {SubscriptService} from '../../services/subscriptService/subscript.service';
import {ModalService} from "../../services/modalService/modal.service";
import {AuthorizationService} from "../../services/authorizationService/authorization.service";
import {BillingAccountService} from "../../services/billingAccountService/billingAccount.service";
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-subscript',
  templateUrl: './subscript.component.html',
  styleUrls: ['./subscript.component.css']
})
export class SubscriptComponent implements OnInit {
  public subscripts: Subscript[];

  constructor(private subscriptService: SubscriptService, public modalService: ModalService,
              public authService: AuthorizationService, public billingAccountService: BillingAccountService,
              private toastr: ToastrService) {
  }

  ngOnInit() {
    this.loadSubscripts();
  }

  onChanged(){
    this.loadSubscripts();
  }
  private loadSubscripts(): void {
    this.subscriptService.getSubscripts().subscribe(data => {
      this.subscripts = data;
    }, error => {
      this.toastr.error('Приносим извинения за неудобства', 'Ошибка сервера');
    });
  }

  public openModalSubscript(template: TemplateRef<any>, subscript: Subscript): void {
    this.modalService.openModal(template);
    this.subscriptService.selectedSubscript = Subscript.cloneSubscript(subscript);
  }


}
