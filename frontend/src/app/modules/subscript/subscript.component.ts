import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {Subscript} from '../models/subscript';
import {SubscriptService} from '../../services/subscriptService/subscript.service';
import {ModalService} from "../../services/modalService/modal.service";
import {AuthorizationService} from "../../services/authorizationService/authorization.service";
import {BillingAccountService} from "../../services/billingAccountService/billingAccount.service";
import {ToastrService} from 'ngx-toastr';
import {User} from '../models/user';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-subscript',
  templateUrl: './subscript.component.html',
  styleUrls: ['./subscript.component.css']
})
export class SubscriptComponent implements OnInit, OnDestroy {

  public subscripts: Subscript[];
  authorizedUser: User = this.authService.authorizedUser;
  selectedSubscript: Subscript;
  private subscriptions: Subscription[] = [];

  constructor(private subscriptService: SubscriptService, public modalService: ModalService,
              public authService: AuthorizationService, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.loadSubscripts();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  onChanged(){
    this.loadSubscripts();
  }

  private loadSubscripts(): void {
    this.subscriptions.push(this.subscriptService.getSubscripts().subscribe(data => {
      this.subscripts = data;
    }, error => {
      this.toastr.error('Приносим извинения за неудобства', 'Ошибка сервера');
    }));
  }

  public openModalSubscript(template: TemplateRef<any>, subscript: Subscript): void {
    this.modalService.openModal(template);
    this.selectedSubscript = subscript;
  }

  public checkSubscript(subscript: Subscript): boolean{
    return this.authService.checkSubscript(subscript);
  }


}
