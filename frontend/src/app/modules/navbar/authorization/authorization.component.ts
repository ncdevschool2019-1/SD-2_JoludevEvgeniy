import {Component, OnInit} from '@angular/core';
import {ModalService} from "../../../services/modalService/modal.service";
import {AuthorizationService} from '../../../services/authorizationService/authorization.service';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html',
  styleUrls: ['./authorization.component.css']
})
export class AuthorizationComponent implements OnInit {

  public inputLogin : string;
  public inputPassword : string;

  constructor(private modalService: ModalService, private authService: AuthorizationService) {
  }

  ngOnInit() {
  }

  authorization(){
    this.authService.authorization(this.inputLogin, this.inputPassword);
    this.closeModal();
  }

  closeModal(){
    this.modalService.closeModal();
    this.inputLogin = "";
    this.inputPassword = "";
  }

}
