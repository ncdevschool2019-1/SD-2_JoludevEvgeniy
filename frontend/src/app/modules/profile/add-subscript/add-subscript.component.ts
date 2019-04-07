import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscript} from '../../models/subscript';
import {ModalService} from '../../../services/modalService/modal.service';
import {SubscriptService} from '../../../services/subscriptService/subscript.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-add-subscript',
  templateUrl: './add-subscript.component.html'
})
export class AddSubscriptComponent implements OnInit, OnDestroy {


  selectedSubscript: Subscript = new Subscript();
  private subscriptions: Subscription[] = [];

  constructor(public modalService: ModalService, public subscriptService: SubscriptService,
              private toastr: ToastrService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }

  public closeModal() {
    this.modalService.closeModal();
    this.selectedSubscript = new Subscript();
  }

  createSubscript(subscript: Subscript) {
    this.subscriptions.push(this.subscriptService.saveSubscript(subscript).subscribe(data => {
      this.closeModal();
      this.toastr.success('Подписка успешно создана!', data.name);
    }, error => {
      this.toastr.error('Создать подписку не удалось', 'Ошибка');
    }));
  }

}
