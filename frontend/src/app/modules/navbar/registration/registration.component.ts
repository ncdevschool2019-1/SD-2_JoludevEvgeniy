import {Component, OnInit} from '@angular/core';
import {ModalService} from '../../../services/modalService/modal.service';
import {User} from '../../models/user';
import {UserService} from '../../../services/userService/user.service';
import {AuthorizationService} from '../../../services/authorizationService/authorization.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private user: User = new User;

  constructor(private modalService: ModalService, private userService: UserService,
              private authService: AuthorizationService) {
  }

  ngOnInit() {
  }

  closeModal() {
    this.modalService.closeModal();
    this.user = new User;
  }

  createUser() {
    this.userService.saveUser(this.user).subscribe(data => {
      this.closeModal();
      this.authService.authorization(data.login, data.password);
    });
  }

}
