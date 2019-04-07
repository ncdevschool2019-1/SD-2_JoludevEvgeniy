import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthorizationService} from '../../services/authorizationService/authorization.service';

import {ModalService} from '../../services/modalService/modal.service';
import {User} from '../models/user';
import {UserService} from '../../services/userService/user.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {

  public newLogin: string;
  public newEmail: string;
  public oldPassword: string;
  public newPassword: string;
  authorizedUser: User = this.authService.authorizedUser;
  private subscriptions: Subscription[] = [];


  constructor(private authService: AuthorizationService, private modalService: ModalService,
              private userService: UserService, private toastr: ToastrService) {
  }


  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }


  changeLogin() {
    this.authorizedUser.login = this.newLogin;
    this.subscriptions.push(this.userService.updateUsersLogin(this.authorizedUser).subscribe(data => {
      this.toastr.success('Ваш логин изменен!', 'Операция выполнена успешно');
      this.authService.authorizedUser = data;
    }, error => {
      this.toastr.error('Изменение логина не удалось', 'Операция не выполнена');
    }));
    this.newLogin = '';
  }

  changePassword() {
    this.authorizedUser.password = this.newPassword;
    this.subscriptions.push(this.userService.updateUsersPassword(this.authorizedUser).subscribe(data => {
      this.toastr.success('Ваш пароль изменен!', 'Операция выполнена успешно');
      this.authService.authorizedUser = data;
    }, error => {
      this.toastr.error('Изменение пароля не удалось', 'Операция не выполнена');
    }));

    this.newPassword = '';
    this.oldPassword = '';
  }

  changeEmail() {
    this.authorizedUser.email = this.newEmail;
    this.subscriptions.push(this.userService.updateUsersEmail(this.authorizedUser).subscribe(data => {

      this.toastr.success('Ваш email изменен!', 'Операция выполнена успешно');
      this.authService.authorizedUser = data;
    }, error => {
      this.toastr.error('Изменение email(а) не удалось', 'Операция не выполнена');
    }));

    this.newEmail = '';
  }


}
