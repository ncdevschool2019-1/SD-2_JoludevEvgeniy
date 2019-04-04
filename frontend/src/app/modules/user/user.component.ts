import {Component, OnInit, TemplateRef} from '@angular/core';
import {UserService} from '../../services/userService/user.service';
import {User} from '../models/user';
import {ModalService} from "../../services/modalService/modal.service";
import {BillingAccountService} from "../../services/billingAccountService/billingAccount.service";
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {


  public users: User[];

  constructor(private userService: UserService, private modalService: ModalService,
              private billingAccountService: BillingAccountService, private toastr: ToastrService) {
  }

  ngOnInit() {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data
    }, error => {
      this.toastr.error('Приносим извинения за неудобства', 'Ошибка сервера');
    });
  }

  public openModal(template: TemplateRef<any>, user: User): void {
    this.modalService.openModal(template);
    this.userService.selectedUser = User.cloneUser(user);
  }

  deleteUser(userId: number){
    this.userService.deleteUser(userId).subscribe(data => {
      this.loadUsers();
      this.toastr.success('Пользователь удалён!', 'Операция выполнена');
    }, error => {
      this.toastr.error('Удалить пользователя не удалось', 'Ошибка')
    })
  }

}
