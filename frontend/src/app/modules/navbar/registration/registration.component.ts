
import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../services/modal.service';
import {User} from '../../models/user';
import {UserService} from '../../../services/user.service';
import {AuthorizationService} from '../../../services/authorization.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  private user: User = new User;
  private subscriptions: Subscription[] = [];

  constructor(private modalService: ModalService, private userService: UserService,
              private authService: AuthorizationService, private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }


  closeModal() {
    this.modalService.closeModal();
    this.user = new User;
  }

  createUser() {
    this.subscriptions.push(this.userService.saveUser(this.user).subscribe(data => {
      this.authService.setAuthUser(data);
      this.closeModal();
      this.toastr.success('Аккаунт успешно создан!', 'Поздравляем');
    }, error => {
      this.toastr.error('Создание аккаунта не удалось', 'Ошибка');
    }));
  }

}
