import { Component, OnInit } from '@angular/core';
import {Subscript} from "../../models/subscript";
import {ModalService} from "../../../services/modalService/modal.service";
import {SubscriptService} from "../../../services/subscriptService/subscript.service";
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-add-subscript',
  templateUrl: './add-subscript.component.html'
})
export class AddSubscriptComponent implements OnInit {

  constructor(public modalService: ModalService, public subscriptService: SubscriptService,
              private toastr: ToastrService) { }

  ngOnInit() {
  }

  public closeModal(){
    this.modalService.closeModal();
    this.subscriptService.clearSelectedSubscript();
  }

  createSubscript(subscript: Subscript){
    this.subscriptService.saveSubscript(subscript).subscribe(data => {
      this.closeModal();
      this.toastr.success('Подписка успешно создана!', data.name);
    }, error => {
      this.toastr.error('Создать подписку не удалось', 'Ошибка');
    })
  }

}
