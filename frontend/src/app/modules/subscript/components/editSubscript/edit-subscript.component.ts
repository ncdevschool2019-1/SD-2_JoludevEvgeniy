import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../../../../services/modalService/modal.service';
import {SubscriptService} from '../../../../services/subscriptService/subscript.service';
import {Subscript} from '../../../models/subscript';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-edit-subscript',
  templateUrl: 'edit-subscript.component.html'
})

export class EditSubscriptComponent implements OnInit, OnDestroy {

  @Output() onChanged = new EventEmitter();
  fileList: FileList;
  private subscriptions: Subscription[] = [];

  @Input() selectedSubscript: Subscript;

  constructor(private subscriptService: SubscriptService, private modalService: ModalService,
              private toastr: ToastrService) {

  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }


  closeModal(): void {
    this.modalService.closeModal();
    this.selectedSubscript = new Subscript();
  }

  uploadImages(event) {
    this.fileList = event.target.files;
  }

  updateSubscript(subscript: Subscript): void {
    this.subscriptions.push(this.subscriptService.saveSubscript(subscript).subscribe(data => {
      this.onChanged.emit();
      this.closeModal();
      this.toastr.success('Вы успешно изменили подписку!', subscript.name);
    }, error => {
      this.toastr.error('К сожалению, подписку изменить не удалось', 'Ошибка');
    }));
    if (this.fileList.length > 0) {
      this.subscriptions.push(this.subscriptService.saveSubscriptsImage(this.fileList[0], subscript.id).subscribe(data => {
        this.toastr.success('Изображение успешно изменено', subscript.name);
      }, error => {
        this.toastr.error('Изображение изменить не удалось', 'Ошибка');
      }));
      this.fileList = undefined;
    }
  }
}
