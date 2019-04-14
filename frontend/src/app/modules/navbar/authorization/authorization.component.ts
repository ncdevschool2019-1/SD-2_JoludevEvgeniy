import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../services/modal.service';
import {AuthorizationService} from '../../../services/authorization.service';
import {UserService} from '../../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {User} from '../../models/user';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit, OnDestroy {


  inputUser: User = new User();
  private subscriptions: Subscription[] = [];

  constructor(private modalService: ModalService, private authService: AuthorizationService,
              private userService: UserService, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  authorization() {
    this.subscriptions.push(this.userService.getLoginUser(this.inputUser).subscribe(data => {
      this.authService.setAuthUser(data);
      this.toastr.success('Вы успешно вошли!', data.login);
    }, error => {
      this.toastr.error('Войти не удалось', 'Ошибка');
    }, () => {
      this.closeModal();
    }));

  }

  closeModal() {
    this.modalService.closeModal();
    this.inputUser = new User();
  }

}
