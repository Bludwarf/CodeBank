import {Component, forwardRef, Input, ViewChild} from '@angular/core';
import {ControlContainer, ControlValueAccessor, FormControl, FormControlDirective, NG_VALUE_ACCESSOR} from '@angular/forms';

@Component({
  selector: 'app-slide-toggle',
  templateUrl: './slide-toggle.component.html',
  styleUrls: ['./slide-toggle.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SlideToggleComponent),
      multi: true
    }
  ]
})
export class SlideToggleComponent implements ControlValueAccessor {

  @Input()
  formControlName: string;

  @Input()
  formControl: FormControl;

  @ViewChild(FormControlDirective, {static: true})
  formControlDirective: FormControlDirective;

  constructor(
    private controlContainer: ControlContainer,
  ) {
  }

  get angularFormControl(): FormControl {
    return this.formControl || this.controlContainer.control.get(this.formControlName) as FormControl;
  }

  registerOnChange(fn: (_: any) => void): void {
    this.formControlDirective.valueAccessor.registerOnChange(fn);
  }

  registerOnTouched(fn: any): void {
    this.formControlDirective.valueAccessor.registerOnTouched(fn);
  }

  writeValue(obj: any): void {
    this.formControlDirective.valueAccessor.writeValue(obj);
  }
}
