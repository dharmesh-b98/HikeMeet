import { AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { HikeSpot } from '../../model/hikespot';

@Component({
  selector: 'app-hikespotsmap',
  standalone: false,
  templateUrl: './hikespotsmap.component.html',
  styleUrl: './hikespotsmap.component.css'
})
export class HikespotsmapComponent implements OnChanges{
  @Input({required:true})
  hikeSpotList!: HikeSpot[]

  @Input({required:true})
  filterBy!: string

  center!: google.maps.LatLngLiteral
  zoom!: number
  markers !: any[] 

  ngOnChanges(): void {
    if (this.hikeSpotList){
      this.center = this.getCenterCoordinates()
      this.zoom = this.getMapZoom()
      this.markers = this.getMarkers()
    }
  }

  getCenterCoordinates(): google.maps.LatLngLiteral{
    let latSum = 0.0;
    let lngSum = 0.0;
    for (let hikeSpot of this.hikeSpotList){
        latSum += hikeSpot.lat;
        lngSum += hikeSpot.lng;
    }
    let latAve = latSum/this.hikeSpotList.length;
    let lngAve = lngSum/this.hikeSpotList.length;

    const output = { lat: latAve, lng: lngAve } as google.maps.LatLngLiteral

    return output;
  }


  getMapZoom(): number{
    if (this.filterBy == "Japan"){
        return 4;
    }
    else if (this.filterBy == "India"){
        return 3.5;
    }
    else if (this.filterBy == "Singapore") {
        return 10;
    }
    return 3;
  }

  
  getMarkers(){
    let markerlist: any[] = []
    for (let hikeSpot of this.hikeSpotList){
      markerlist.push({position:{lat: hikeSpot.lat, lng: hikeSpot.lng},title:hikeSpot.name})
    }
    return markerlist;
  }
}
