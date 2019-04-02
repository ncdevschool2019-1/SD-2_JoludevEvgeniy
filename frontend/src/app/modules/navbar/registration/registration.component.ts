import {Component, OnInit} from '@angular/core';
import {ModalService} from "../../../services/modalService/modal.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  public inputLogin: string;
  public inputPassword: string;
  public inputEmail: string;

  constructor(public modalService: ModalService) {
  }

  ngOnInit() {
  }

}
