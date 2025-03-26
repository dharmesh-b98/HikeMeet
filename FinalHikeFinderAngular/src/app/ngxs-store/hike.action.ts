import { Hike } from "../model/hike";

//Read
export class GetHikes {
    static readonly type = '[Hikes] Fetch';
}

//Create
export class AddHikes {
    static readonly type = '[Hikes] Add';
    constructor(public payload: Hike) { }
}

//Update
export class UpdateHikesJoin {
    static readonly type = '[Hikes] Update';
    constructor(public id: number, public username: string) { }
}

//Update
export class UpdateHikesUnjoin {
    static readonly type = '[Hikes] Update';
    constructor(public id: number, public username: string) { }
}

//Delete
export class DeleteHikes {
    static readonly type = '[Hikes] Delete';
    constructor(public id: number) { }
}