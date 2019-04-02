import {Injectable, TemplateRef} from "@angular/core";
import {BsModalRef, BsModalService} from "ngx-bootstrap";

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  public modalRef: BsModalRef;

  constructor(private modalService: BsModalService) {
  }

  public openModal(template: TemplateRef<any>): void {
    this.modalRef = this.modalService.show(template, {backdrop: 'static'});
  }

  public closeModal() {
    this.modalRef.hide()
  }


}
