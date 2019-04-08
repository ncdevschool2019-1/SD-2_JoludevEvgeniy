import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {UserService} from '../../services/user.service';
import {User} from '../models/user';
import {ModalService} from '../../services/modal.service';
import {BillingAccountService} from '../../services/billingAccount.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit, OnDestroy {


  public users: User[];
  selectedUser: User;
  private subscriptions: Subscription[] = [];

  constructor(private userService: UserService, private modalService: ModalService,
              private billingAccountService: BillingAccountService, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.loadUsers();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  onChanged(){
    this.loadUsers();
  }

  private loadUsers(): void {
    this.subscriptions.push(this.userService.getUsers().subscribe(data => {
      this.users = data;
    }, error => {
      this.toastr.error('Приносим извинения за неудобства', 'Ошибка сервера');
    }));
  }

  public openModal(template: TemplateRef<any>, user: User): void {
    this.modalService.openModal(template);
    this.selectedUser = user;
  }

  deleteUser(userId: number) {
    this.subscriptions.push(this.userService.deleteUser(userId).subscribe(data => {
      this.loadUsers();
      this.toastr.success('Пользователь удалён!', 'Операция выполнена');
    }, error => {
      this.toastr.error('Удалить пользователя не удалось', 'Ошибка');
    }));
  }

}
