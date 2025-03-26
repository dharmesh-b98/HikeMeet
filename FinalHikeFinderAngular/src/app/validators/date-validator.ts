import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function dateValidator(timeState: string): ValidatorFn{
    return (control: AbstractControl): ValidationErrors | null => {
        if (!control.value) return null;
        if (control.value === '') return null;

        const currentDate = new Date()
        const inputDate = new Date(control.value)

        if (timeState == "Future"){
            if (inputDate > currentDate){
                return null;
            }
            else {
                return {timeStateCheck: {timeState} }
            }
        }
        else if (timeState == "Past"){
            if (inputDate < currentDate){
                return null;
            }
            else {
                return {timeStateCheck: {timeState} }
            }
        }
        else{//present
            if (inputDate == currentDate){
                return null;
            }
            else {
                return {timeStateCheck: {timeState} } //timeStateCheck is the error name that can be referenced in the html
            }
        }
    }
}