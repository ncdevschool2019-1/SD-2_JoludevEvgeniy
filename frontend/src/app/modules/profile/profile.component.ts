import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthorizationService} from '../../services/authorization.service';

import {ModalService} from '../../services/modal.service';
import {User} from '../models/user';
import {UserService} from '../../services/user.service';
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
  authorizedUser: User;
  private subscriptions: Subscription[] = [];
  fileList: FileList;


  constructor(private authService: AuthorizationService, private modalService: ModalService,
              private userService: UserService, private toastr: ToastrService) {
  }


  ngOnInit() {
    this.getAuthUser();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  getAuthUser(){
    this.authorizedUser = this.authService.authorizedUser;
  }

  uploadImage(event) {
    this.fileList = event.target.files;
    if (this.fileList && this.fileList.length > 0) {
      this.subscriptions.push(this.userService.saveUsersImage(this.fileList[0], this.authorizedUser.id).subscribe(value => {
        this.authService.authorizedUser = value;
        this.getAuthUser();
        this.toastr.success('Фото успешно сохранено','Успех!');
      }, error => {
        this.toastr.error('Загрузить фото не удалось', 'Ошибка');
      }));
    }
  }

  changeLogin() {
    this.authorizedUser.login = this.newLogin;
    this.subscriptions.push(this.userService.updateUsersLogin(this.authorizedUser).subscribe(data => {
      this.toastr.success('Ваш логин изменен!', 'Операция выполнена успешно');
      this.authService.authorizedUser = data;
      this.getAuthUser();
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
      this.getAuthUser();
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
      this.getAuthUser();
    }, error => {
      this.toastr.error('Изменение email(а) не удалось', 'Операция не выполнена');
    }));

    this.newEmail = '';
  }


}
