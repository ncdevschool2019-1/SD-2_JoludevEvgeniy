import {Component, OnInit} from '@angular/core';
import {AuthorizationService} from '../../services/authorizationService/authorization.service';

import {ModalService} from '../../services/modalService/modal.service';
import {User} from '../models/user';
import {UserService} from '../../services/userService/user.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public newLogin: string;
  public newEmail: string;
  public oldPassword: string;
  public newPassword: string;
  updatableUser: User = User.cloneUser(this.authService.authorizedUser);


  constructor(private authService: AuthorizationService, private modalService: ModalService,
              private userService: UserService, private toastr: ToastrService) {
  }


  ngOnInit() {
  }

  changeLogin() {
    this.updatableUser.login = this.newLogin;
    this.userService.updateUsersLogin(this.updatableUser).subscribe(data => {
      this.authService.updateAuthorization();
      this.newLogin = '';
      this.toastr.success('Ваш логин изменен!', 'Операция выполнена успешно');
    }, error => {
      this.toastr.error('Изменение логина не удалось', 'Операция не выполнена');
    });
  }

  changePassword() {
    this.updatableUser.password = this.newPassword;
    this.userService.updateUsersPassword(this.updatableUser).subscribe(data => {
      this.authService.updateAuthorization();
      this.newPassword = '';
      this.oldPassword = '';
      this.toastr.success('Ваш пароль изменен!', 'Операция выполнена успешно');
    }, error => {
      this.toastr.error('Изменение пароля не удалось', 'Операция не выполнена');
    });
  }

  changeEmail() {
    this.updatableUser.email = this.newEmail;
    this.userService.updateUsersEmail(this.updatableUser).subscribe(data => {
      this.authService.updateAuthorization();
      this.newEmail = '';
      this.toastr.success('Ваш email изменен!', 'Операция выполнена успешно');
    }, error => {
      this.toastr.error('Изменение email(а) не удалось', 'Операция не выполнена');
    });
  }


}
