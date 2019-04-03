import {Component, OnInit} from '@angular/core';
import {ModalService} from "../../services/modalService/modal.service";
import {AuthorizationService} from "../../services/authorizationService/authorization.service";
import {LocalizationService} from "../../services/localizationService/localization.service";


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private modalService: ModalService, private authService: AuthorizationService) {
  }


  ngOnInit() {
  }

  outFromAccount(){
    this.authService.outFromAccount();
  }

}
