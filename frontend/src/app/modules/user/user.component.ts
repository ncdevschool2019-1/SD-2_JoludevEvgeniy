import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../models/user';
import {ModalService} from '../../services/modal.service';
import {BillingAccountService} from '../../services/billingAccount.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {


  pages: number[];
  currentPage: number = 1;

  public users: User[] = [];
  selectedUser: User;
  private subscriptions: Subscription[] = [];

  constructor(private userService: UserService, private modalService: ModalService,
              private billingAccountService: BillingAccountService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService) {
  }

  ngOnInit() {
    let maxPage: number;
    this.subscriptions.push(this.userService.getMaxPage().subscribe(data => {
      maxPage = data;
      this.pages = Array(maxPage).fill(null).map((x, i) => i + 1);
      this.loadUsers(this.currentPage);
    }));

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  onChanged() {
    this.loadUsers(this.currentPage);
  }

  private loadUsers(page: number): void {
    this.loadingService.show();
    this.currentPage = page;
    this.subscriptions.push(this.userService.getUsers(this.currentPage).subscribe(data => {
      this.users = data;
    }, error => {
      this.toastr.error('We sorry for inconvenience', 'Server error');
      this.loadingService.hide();
    }, () => this.loadingService.hide()));
  }

  public openModal(template: TemplateRef<any>, user: User): void {
    this.modalService.openModal(template);
    this.selectedUser = user;
  }

  deleteUser(userId: number) {
    this.loadingService.show();
    this.subscriptions.push(this.userService.deleteUser(userId).subscribe(data => {
      this.loadUsers(this.currentPage);
      this.toastr.success('User is deleted', 'Success');
    }, error => {
      this.toastr.error('User is not deleted', 'Error');
      this.loadingService.hide();
    }, () => this.loadingService.hide()));
  }

}
