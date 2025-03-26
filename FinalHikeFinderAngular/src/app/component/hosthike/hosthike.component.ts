import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HikeSpot } from '../../model/hikespot';
import { HikespotlistService } from '../../service/hikespotlist.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { dateValidator } from '../../validators/date-validator';
import { HikeService } from '../../service/hike.service';
import { Hike } from '../../model/hike';
import { Store } from '@ngxs/store';
import { AddHikes } from '../../ngxs-store/hike.action';

@Component({
  selector: 'app-hosthike',
  standalone: false,
  templateUrl: './hosthike.component.html',
  styleUrl: './hosthike.component.css'
})
export class HosthikeComponent implements OnInit{
  
  activatedRoute = inject(ActivatedRoute)
  hikespotlistService = inject(HikespotlistService)
  hikeService = inject(HikeService)
  fb = inject(FormBuilder)
  router = inject(Router)
  private store = inject(Store)

  hikeSpotId: number = this.activatedRoute.snapshot.params["hikeSpotId"]
  hikeSpot!:HikeSpot
  form!:FormGroup


  ngOnInit(): void {
    this.getHikeSpot()
    this.createForm()
  }


  getHikeSpot(){
    this.hikespotlistService.getHike(this.hikeSpotId).subscribe(
      (data) => {
        this.hikeSpot = data
        console.log(data)
      }
    )
  }


  createForm(){
    this.form = this.fb.group({
      dateAndTime: this.fb.control<string>("",[Validators.required, dateValidator("Future")]),
      sunriseTime: this.fb.control<string>("",[Validators.required]),
      sunsetTime: this.fb.control<string>("",[Validators.required])
    })
  }


  generateSunTimings(){
    if (this.form.get("dateAndTime")?.valid){
      console.log(this.form.value.dateAndTime)
      this.hikeService.getSunTimings(this.form.value.dateAndTime, this.hikeSpot).subscribe(
        (data) => {
          console.log(data)
          console.log(data.results.sunrise)
          this.form.get("sunriseTime")?.patchValue(data.results.sunrise.substring(0,16))
          this.form.get("sunsetTime")?.patchValue(data.results.sunset.substring(0,16))
          console.log(this.form.value.sunriseTime)
        }
      )
    }
    
  }

  
  hostHike(){
    let hike: Hike = {} as Hike
    hike.host = sessionStorage.getItem("loggedIn") ?? ""
    hike.country = this.hikeSpot.country
    hike.hikeSpotName = this.hikeSpot.name
    hike.dateAndTime = new Date(this.form.value.dateAndTime)
    hike.sunriseTime = new Date(this.form.value.sunriseTime)
    hike.sunsetTime = new Date(this.form.value.sunsetTime)
    hike.usersJoined = [hike.host]

    /* this.hikeService.hostHike(hike).subscribe(
      (data) => {
        console.log(data)
        this.router.navigate(['/hostedhikes'])
      }
    ) */

    this.store.dispatch(new AddHikes(hike)); //ADD HIKE TO STORE
    this.router.navigate(['/hostedhikes'])
  }
  
}
