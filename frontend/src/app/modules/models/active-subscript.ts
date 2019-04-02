import {Subscript} from './subscript';
import {DatePipe} from '@angular/common';

export class ActiveSubscript{
  id: number;
  billingAccountId: number;
  subscript: Subscript;
  lastWriteOff: Date;

  static cloneActiveSubscript(activeSubscript: ActiveSubscript): ActiveSubscript{
    let clonedActiveSubscript : ActiveSubscript;
    clonedActiveSubscript.id = activeSubscript.id;
    clonedActiveSubscript.billingAccountId = activeSubscript.billingAccountId;
    clonedActiveSubscript.lastWriteOff = activeSubscript.lastWriteOff;
    clonedActiveSubscript.subscript = activeSubscript.subscript;
    return clonedActiveSubscript;
  }
}

