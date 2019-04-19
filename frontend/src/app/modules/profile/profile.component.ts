import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthorizationService} from '../../services/authorization.service';

import {ModalService} from '../../services/modal.service';
import {User} from '../models/user';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {ChangePasswordUser} from '../models/ChangePasswordUser';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';

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
  authorizedUser: User = new User();
  private subscriptions: Subscription[] = [];
  fileList: FileList;


  constructor(private authService: AuthorizationService, private modalService: ModalService,
              private userService: UserService, private toastr: ToastrService,
              private loadingService: Ng4LoadingSpinnerService) {
  }




  ngOnInit() {
    this.getAuthUser();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  getAuthUser() {
    this.subscriptions.push(this.authService.subscribeToAuthUser().subscribe(value => {
      this.authorizedUser = value;
    }));
    this.authService.getAuthUser();
  }

  uploadImage(event) {
    this.loadingService.show();
    this.fileList = event.target.files;
    if (this.fileList && this.fileList.length > 0) {
      this.subscriptions.push(this.userService.saveUsersImage(this.fileList[0], this.authorizedUser.id).subscribe(value => {
        this.authorizedUser = value;
        this.authService.setAuthUser(this.authorizedUser);
        this.toastr.success('Фото успешно сохранено', 'Успех!');
      }, error => {
        this.toastr.error('Загрузить фото не удалось', 'Ошибка');
      }, () => this.loadingService.hide()));
    }
  }

  changeLogin() {
    this.loadingService.show();
    let user = User.cloneUser(this.authorizedUser);
    user.login = this.newLogin;
    this.subscriptions.push(this.userService.updateUsersLogin(user).subscribe(data => {
      this.authorizedUser = data;
      this.authService.setAuthUser(this.authorizedUser);
      this.newLogin = '';
      this.toastr.success('Ваш логин изменен!', 'Операция выполнена успешно');
    }, error => {
      this.toastr.error('Изменение логина не удалось', 'Операция не выполнена');
    }, () => this.loadingService.hide()));
  }

  changePassword() {
    this.loadingService.show();
    let changePasswordUser: ChangePasswordUser = new ChangePasswordUser();
    changePasswordUser.userId = this.authorizedUser.id;
    changePasswordUser.newPassword = this.newPassword;
    changePasswordUser.oldPassword = this.oldPassword;
    this.subscriptions.push(this.userService.updateUsersPassword(changePasswordUser).subscribe(data => {
      this.authorizedUser = data;
      this.authService.setAuthUser(this.authorizedUser);
      this.newPassword = '';
      this.oldPassword = '';
      this.toastr.success('Ваш пароль изменен!', 'Операция выполнена успешно');
    }, error => {
      this.toastr.error('Изменение пароля не удалось', 'Операция не выполнена');
    }, () => this.loadingService.hide()));


  }

  changeEmail() {
    this.loadingService.show();
    let user = User.cloneUser(this.authorizedUser);
    user.email = this.newEmail;
    this.subscriptions.push(this.userService.updateUsersEmail(user).subscribe(data => {
      this.authorizedUser = data;
      this.authService.setAuthUser(this.authorizedUser);
      this.newEmail = '';
      this.toastr.success('Ваш email изменен!', 'Операция выполнена успешно');
    }, error => {
      this.toastr.error('Изменение email(а) не удалось', 'Операция не выполнена');
    }, () => this.loadingService.hide()));


  }


}
