import {Component, Input, OnInit} from '@angular/core';
import {User} from './modules/models/user';
import {AuthorizationService} from './services/authorizationService/authorization.service';
import {LocalizationService} from './services/localizationService/localization.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {


  constructor(private localization: LocalizationService) {
  }

  ngOnInit() {
  }


}
