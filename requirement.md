# PSIC

- [x] **`Person`**
- [x] **`Physician`**
- [ ] **`Patient`** :construction: ~WIP~
- [ ] **`Visitor`**
- [ ] **`Appointment`**
- [ ] **`Room`**



- `Member`
  - __UUID__:       String
  - __FullName__:   String
  - __Address__:    String
  - __Tel__:        String

- `Physician` : `Member`
  - [static] __Physicians__:                   `List<Physicians>` <br/>
  *[getter, setter]*

  - [static] __FindPhysicianByName()__:        `List<Physician>` <br/>
  *[same name issue]*

  - [static] __FindPhysicianByExpertise()__:   `List<Physician>`  <br/>

  - [public] __BookedAppointments__:           `List<Appointment>` <br/>
  *[Centralize appointments in `Appointments`, then a getter here]*
  - [public] __Expertise__:                    Enum

  - [public] __Treatments__:                   Enum <br/>
  *[All treatments have a fixed duration of 2hrs.]*

  - [public] __ConsultationHours__ <br/>
  *[2 hrs / physician / week]*


  - __CheckIn()__

- `Patient` : `Member`
  - static
    - __Patients__: `List<Patient>` <br/>
    *[getter, setter]*




- `Visitor`
  - FullName


- __*Appointment*__
*[Working hour: 10 - 18]*
*[Each appointment: 2hrs]*
*[Each consultation: 30mins]*

indexed by physician id
indexed by patient id

  [static]
  - __Appointments__:     `List<Appointment>` <br/>

  [public]
  - __StartTime__
  - __EndTime__
  - __Patient__
  - __Physician__
  - __Room__:             `Room`
  - __Treatment__:        `Treatment`
  - __Attended__:         Bool

- `Room`
  - __ID__
  - __Name__
  - __BookedAppointments__