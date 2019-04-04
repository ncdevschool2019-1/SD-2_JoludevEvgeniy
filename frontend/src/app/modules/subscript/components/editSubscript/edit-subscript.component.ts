import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ModalService} from "../../../../services/modalService/modal.service";
import {SubscriptService} from "../../../../services/subscriptService/subscript.service";
import {Subscript} from '../../../models/subscript';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-edit-subscript',
  templateUrl: 'edit-subscript.component.html'
})

export class EditSubscriptComponent implements OnInit {

  @Output() onChanged = new EventEmitter();

  constructor(private subscriptService: SubscriptService, private modalService: ModalService,
              private toastr: ToastrService) {

  }

  ngOnInit(): void {
  }

  closeModal(): void {
    this.modalService.closeModal();
    this.subscriptService.clearSelectedSubscript();
  }

  updateSubscript(subscript: Subscript): void{
    this.subscriptService.saveSubscript(subscript).subscribe(data => {
      this.onChanged.emit();
      this.closeModal();
      this.toastr.success('Вы успешно изменили подписку!', subscript.name);
    }, error => {
      this.toastr.error('К сожалению, подписку изменить не удалось', 'Ошибка');
    })
  }
}
