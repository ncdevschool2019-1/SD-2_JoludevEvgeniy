import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscript} from '../../models/subscript';
import {ModalService} from '../../../services/modal.service';
import {SubscriptService} from '../../../services/subscript.service';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-add-subscript',
  templateUrl: './add-subscript.component.html'
})
export class AddSubscriptComponent implements OnInit, OnDestroy {


  selectedSubscript: Subscript = new Subscript();
  private subscriptions: Subscription[] = [];
  fileList: FileList;


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

  uploadImages(event) {
    this.fileList = event.target.files;
  }


  createSubscript(subscript: Subscript) {

    this.subscriptions.push(this.subscriptService.saveSubscript(subscript).subscribe(data => {
      subscript.id = data.id;
      if (this.fileList && this.fileList.length > 0) {
        this.subscriptions.push(this.subscriptService.saveSubscriptsImage(this.fileList[0], subscript.id).subscribe(value => {
          this.toastr.success('Изображение успешно загружено', subscript.name);
        }, error => {
          this.toastr.error('Изображение загрузить не удалось', 'Ошибка');
        }));
        this.fileList = undefined;
      }
      this.toastr.success('Подписка успешно создана!', data.name);
    }, error => {
      this.toastr.error('Создать подписку не удалось', 'Ошибка');
    }, () => {
      this.closeModal();
    }));



  }

}
