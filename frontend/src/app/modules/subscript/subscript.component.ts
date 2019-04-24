import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {Subscript} from '../models/subscript';
import {SubscriptService} from '../../services/subscript.service';
import {ModalService} from '../../services/modal.service';
import {AuthorizationService} from '../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {User} from '../models/user';
import {Subscription} from 'rxjs';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';

@Component({
  selector: 'app-subscript',
  templateUrl: './subscript.component.html',
  styleUrls: ['./subscript.component.css']
})
export class SubscriptComponent implements OnInit, OnDestroy {

  public subscripts: Subscript[];
  authorizedUser: User = new User();
  selectedSubscript: Subscript;
  private subscriptions: Subscription[] = [];

  constructor(private subscriptService: SubscriptService, private modalService: ModalService,
              private authService: AuthorizationService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService) {
  }

  ngOnInit() {
    this.getAuthUser();
    this.loadSubscripts();
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

  onChanged() {
    this.loadSubscripts();
  }

  deleteSubscript(subscript: Subscript) {
    this.loadingService.show();
    this.subscriptions.push(this.subscriptService.deleteSubscript(subscript.id).subscribe(value => {
      this.loadSubscripts();
      this.toastr.success('Subscript was deleted', 'Success');
    }, error => {
      this.toastr.error('Subscript deleted is failed', 'Error');
    }, () => this.loadingService.hide()));
  }

  private loadSubscripts(): void {
    this.loadingService.show();
    this.subscriptions.push(this.subscriptService.getSubscripts().subscribe(data => {
      this.subscripts = data;
    }, error => {
      this.toastr.error('We are sorry for inconvenience', 'Server error');
    }, () => this.loadingService.hide()));

  }

  public openModalSubscript(template: TemplateRef<any>, subscript: Subscript): void {
    this.modalService.openModal(template);
    this.selectedSubscript = subscript;
  }

  public checkSubscript(subscript: Subscript): boolean {
    return this.authService.checkSubscript(subscript);
  }


}
