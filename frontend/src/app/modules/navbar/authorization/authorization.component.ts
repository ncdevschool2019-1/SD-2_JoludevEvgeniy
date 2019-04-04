import {Component, OnInit} from '@angular/core';
import {ModalService} from "../../../services/modalService/modal.service";
import {AuthorizationService} from '../../../services/authorizationService/authorization.service';
import {UserService} from '../../../services/userService/user.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {

  public inputLogin : string;
  public inputPassword : string;

  constructor(private modalService: ModalService, private authService: AuthorizationService,
              private userService: UserService, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  authorization(){
    this.userService.getLoginUser(this.inputLogin, this.inputPassword).subscribe(data => {
      this.authService.authorizedUser = data;
      this.closeModal();
      this.toastr.success('Вы успешно вошли!', data.login);
    }, error => {
      this.toastr.error('Войти не удалось', 'Ошибка')
    });

  }

  closeModal(){
    this.modalService.closeModal();
    this.inputLogin = "";
    this.inputPassword = "";
  }

}
