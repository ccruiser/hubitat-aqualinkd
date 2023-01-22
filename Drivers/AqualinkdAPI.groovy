/*
* AqualinkdAPI
*
* Description:
* This Hubitat driver allows polling of the AqualinkD API
*
* Overall Setup:
* 1) Add the AqualinkdAPI.groovy 
* 2) Set the varible options for the endpoint and devices (currently set to seven)
*
* Licensing:
* Copyright 2023 Casey Cruise
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
* on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
* for the specific language governing permissions and limitations under the License.
*
* Known Issue(s) & Gaps:
* 1) Add Dynamic lists for aqualink buttons / inputs
* 2) Add a Virtual Device(s)  :: TODO Task
* 
* Version Control:
* 0.1.0 - Initial version based on logic from UnifiNetworkAPI
* 
* Thank you(s):
* David Snell for examples on polling and well defined structure
*
*/

// Returns the driver name
def DriverName(){
    return "AqualinkdAPI"
}

//this is a lazy attemot to ensure the 200 response is correct
def AqualinkDVerName() {
    return "aqualinkd_version"
}

// Returns the driver version
def DriverVersion(){
    return "0.1.00"
}

//AqualinkD Static JSON items
def AqualinkDDate() {
    return "date"
}
def AqualinkDTime() {
    return "time"
}
def AqualinkDTempUnits() {
    return "temp_units"
}
def AqualinkDPoolHtrSetPnt() {
    return "pool_htr_set_pnt"
}
def AqualinkDSpaHtrSetPnt() {
    return "spa_htr_set_pnt"
}
def AqualinkDFrzProtectSetPnt() {
    return "frz_protect_set_pnt"
}
def AqualinkDFrzProtectStatus() {
    return "Freeze_Protect"
}
def AqualinkDTempAir() {
    return "air_temp"
}
def AqualinkDTempPool() {
    return "pool_temp"
}
def AqualinkDTempSpa() {
    return "spa_temp"
}
def AqualinkDBattery() {
    return "battery"
}
def AqualinkDHrdwVersion() {
    return "version"
}

// Driver Metadata
metadata{
	definition( name: "AqualinkdAPI", namespace: "ccruiser", author: "Casey Cruise", importUrl: "https://raw.githubusercontent.com/ccruiser/hubitat-aqualinkd/main/Drivers/AqualinkdAPI.groovy" ) {
		// Indicate what capabilities the device should be capable of
		capability "Sensor"
		capability "Refresh"
        capability "Actuator"
        
        // Commands
        command "DoSomething" // Does something for development/testing purposes, should be commented before publishing
        command "CallStatusAPI" // Status API Call; WIP cookie and session setup
   
		// Attributes for the driver itself
		attribute "Driver Name", "string" // Identifies the driver being used for update purposes
		attribute "Driver Version", "string" // Handles version for driver
        attribute "Driver Status", "string" // Handles version notices for driver
        // Attributes for the AqualinkD device
        attribute "AqualinkD Date", "string" // Holds the value of the status date
        attribute "AqualinkD Time", "string" // Holds the value of the status time
        attribute "AqualinkD Hardware", "string" // Holds the value of the version controller 
        attribute "AqualinkD Version", "string" // Holds the value of the version of the daemon code
       
    }
	preferences{
		section{
            if( ShowAllPreferences || ShowAllPreferences == null ){ // Show the preferences options
                input( type: "enum", name: "RefreshRate", title: "<b>AqualinkD Stats Refresh Rate</b>", required: false, multiple: false, options: [ "5 minutes", "10 minutes", "15 minutes", "30 minutes", "1 hour", "3 hours", "Manual" ], defaultValue: "Manual" )
    			input( type: "enum", name: "LogType", title: "<b>Enable Logging?</b>", required: false, multiple: false, options: [ "None", "Info", "Debug", "Trace" ], defaultValue: "Info" )
                input( type: "string", name: "AqualinkdURL", title: "<font color='FF0000'><b>AqualinkD IP/Hostname</b></font>", required: true )
                input( type: "string", name: "AqualinkdPort", title: "<font color='FF0000'><b>AqualinkD Port #</b></font>", defaultValue: "80", required: false )
                input( type: "string", name: "AqualinkdDevice1", title: "<font color='FF0000'><b>Include the name of the first device</b></font>", defaultValue: "Filter_Pump", required: false )
                input( type: "string", name: "AqualinkdDevice2", title: "<font color='FF0000'><b>Include the name of the second device</b></font>", defaultValue: "Spa_Mode", required: false )
                input( type: "string", name: "AqualinkdDevice3", title: "<font color='FF0000'><b>Include the name of the third device</b></font>", defaultValue: "Aux_1", required: false )
                input( type: "string", name: "AqualinkdDevice4", title: "<font color='FF0000'><b>Include the name of the fourth device</b></font>", defaultValue: "Aux_2", required: false )
                input( type: "string", name: "AqualinkdDevice5", title: "<font color='FF0000'><b>Include the name of the fifth device</b></font>", defaultValue: "Aux_3", required: false )
                input( type: "string", name: "AqualinkdDevice6", title: "<font color='FF0000'><b>Include the name of the sixth device</b></font>", defaultValue: "Pool_Heater", required: false )
                input( type: "string", name: "AqualinkdDevice7", title: "<font color='FF0000'><b>Include the name of the seventh device</b></font>", defaultValue: "Spa_Heater", required: false )
                //input( type: "string", name: "AqualinkdDevice8", title: "<font color='FF0000'><b>Include the name of the eighth device</b></font>", defaultValue: "Aux_8", required: false )
				/*
                input( type: "string", name: "Username", title: "<font color='FF0000'><b>Username</b></font>", required: true )
				input( type: "password", name: "Password", title: "<font color='FF0000'><b>Password</b></font>", required: true )
                */
                /*
                input( type: "bool", name: "AdvancedCommands", title: "<b>Enable Advanced Commands?</b>", description: "Allows controller Reboot and PowerDown commands to run.", defaultValue: false )
                input( type: "bool", name: "UnifiChildren", title: "<b>Show AquaLink Devices as Children?</b>", required: true, defaultValue: false )
                input( type: "bool", name: "EnableClientCheck", title: "<b>Enable Hourly Polling Checks</b>", description: "Creates ClientCheck child and performs hourly checks.", defaultValue: false )
                */
                input( type: "bool", name: "ShowAllPreferences", title: "<b>Show All Preferences?</b>", defaultValue: true )
            } else {
                input( type: "bool", name: "ShowAllPreferences", title: "<b>Show All Preferences?</b>", defaultValue: true )
            }
        }
	}
}

// Just a command to be put fixes or other oddities during development
def DoSomething(){
    Logging( "Using Current Host ${ AqualinkdURL }", 2 )
     httpGet( uri: "http://192.168.11.121/api/status", 
              contentType: "application/json" )          { resp ->
        switch( resp.status ){
            case 200:
                if( resp.data."${ AqualinkDVerName() }" ){
                    // Set General items
                    setAlDaemonVer = resp.data."${ AqualinkDVerName() }"
                    setAlHwrVer = resp.data."${ AqualinkDHrdwVersion() }"
                    setAlDate = resp.data."${ AqualinkDDate()  }"
                    setAlTime = resp.data."${ AqualinkDTime() }"
                    setBatteryStat = resp.data."${ AqualinkDBattery() }"


                    //Add to Device 
                    Logging( "AqualinkD Daemon Status set to "+setAlDaemonVer, 2 )
                    Logging( "AqualinkD date set to "+setAlDate, 2 )
                    Logging( "AqualinkD time set to "+setAlTime, 2 )
                    Logging( "AqualinkD Hardware verison "+setAlHwrVer, 2 )
                    Logging( "AqualinkD Battery "+setBatteryStat, 2 )
                    ProcessEvent( "AqualinkD Version", setAlDaemonVer )
                    ProcessState( "AqualinkD Version", setAlDaemonVer )
                    ProcessState( "AqualinkD Date", setAlDate )
                    ProcessState( "AqualinkD Time", setAlTime )
                    ProcessState( "AqualinkD Hardware", setAlHwrVer )

                    //Set Pool Specific Items
                     setAlTempUnit = resp.data."${ AqualinkDTempUnits() }"
                     setPoolHeaterSetPoint = resp.data."${ AqualinkDPoolHtrSetPnt() }" 
                     setSpaHeaterSetPoint = resp.data."${ AqualinkDSpaHtrSetPnt() }" 
                     setFrzPrtStatus = resp.data."${ AqualinkDFrzProtectStatus() }" 
                     setFrzPrtSetPoint = resp.data."${ AqualinkDFrzProtectSetPnt() }" 
                     setAirTemp = resp.data."${ AqualinkDTempAir() }" 
                     setPoolTemp = resp.data."${ AqualinkDTempPool() }" 
                     setSpaTemp = resp.data."${ AqualinkDTempSpa() }" 

                    Logging( "AqualinkD temp unit set to "+setAlTempUnit, 2 )
                    Logging( "AqualinkD freeze protect status "+setFrzPrtStatus, 2 )
                    Logging( "AqualinkD freeze protect set point to "+setFrzPrtSetPoint, 2 )
                    Logging( "AqualinkD pool heater set point to "+setPoolHeaterSetPoint, 2 )
                    Logging( "AqualinkD spa heater set point to "+setSpaHeaterSetPoint, 2 )
                    Logging( "AqualinkD air temp set to "+setAirTemp, 2 )
                    Logging( "AqualinkD pool temp set to "+setPoolTemp, 2 )
                    Logging( "AqualinkD spa temp set to "+setSpaTemp, 2 )

                   
                } else {
                    Logging( "${ AqualinkDVerName() } was not found from host", 2 )
                    ProcessEvent( "Aqualink Status", "${ AqualinkDVerName() } was not found" )
                }
                break
            default:
                Logging( "Unable to check aqualinkd for ${ AqualinkDVerName() } status.", 2 )
                break
        }
    }

}

// updated is called whenever device parameters are saved
def updated(){
    Logging( "Updating...", 2 )
    ProcessEvent( "Driver Name", "${ DriverName() }" )
    ProcessEvent( "Driver Version", "${ DriverVersion() }" )

    if( LogType == null ){
        LogType = "Info"
    }
    
    // Remove child devices (except Presence and ClientCheck) if previously created and disabled
    /*
    if( UnifiChildren == false ){
        Logging( "Unifi Children disabled, removing Unifi device children", 2 )
        getChildDevices().each{
            if( it.deviceNetworkId.startsWith( "Presence" ) != true || it.deviceNetworkId.startsWith( "ClientCheck" ) != true ){ // Ignore Presence or ClientCheck children
                Logging( "Unifi Children disabled, removing ${ it.deviceNetworkId }", 2 )
                deleteChildDevice( it.deviceNetworkId )
            }
        }
    }
    */
    
    // Remove ClientCheck child if previously created and disabled, create child if not already created and enabled
    /*
    if( EnableClientCheck == false ){
        Logging( "ClientCheck disabled, removing client check child if it exists", 2 )
        deleteChildDevice( "ClientCheck" )
    } else {
        def ClientCheckExists = false
        getChildDevices().each{
            if( it.deviceNetworkId.startsWith( "ClientCheck" ) ){
                ClientCheckExists = true
            }
        }
        if( ClientCheckExists != true ){
            addChild( "ClientCheck", "ClientCheck" )
        }
    }
    */
    // Unschedule any existing activities so they can be reset
    unschedule()
    
    // Perform a login just to make sure everything worked and was saved properly
    // Schedule a login every 10 minutes
    /*
    def Hour = ( new Date().format( "h" ) as int )
    def Minute = ( new Date().format( "m" ) as int )
    def Second = ( new Date().format( "s" ) as int )
    Second = ( (Second + 5) % 60 )
    schedule( "${ Second } 0/10 * ? * *", "Login" )
    */
    
    // Check what the refresh rate is set for then run it
    switch( RefreshRate ){
        case "1 minute": // Schedule the refresh check for every minute
            schedule( "${ Second } * * ? * *", "refresh" )
            break
        case "5 minutes": // Schedule the refresh check for every 5 minutes
            schedule( "${ Second } 0/5 * ? * *", "refresh" )
            break
        case "10 minutes": // Schedule the refresh check for every 10 minutes
            schedule( "${ Second } 0/10 * ? * *", "refresh" )
            break
        case "15 minutes": // Schedule the refresh check for every 15 minutes
            schedule( "${ Second } 0/15 * ? * *", "refresh" )
            break
        case "30 minutes": // Schedule the refresh check for every 30 minutes
            schedule( "${ Second } 0/30 * ? * *", "refresh" )
            break
        case "1 hour": // Schedule the refresh check for every hour
            schedule( "${ Second } ${ Minute } * ? * *", "refresh" )
            break
        case "3 hours": // Schedule the refresh check for every 3 hours
            schedule( "${ Second } ${ Minute } 0/3 ? * *", "refresh" )
            break
        default:
            RefreshRate = "Manual"
            break
    }
    Logging( "Refresh rate: ${ RefreshRate }", 4 )

    // Attempt to call status API
    CallStatusAPI()
     
    //RefreshAllClients()
    //RefreshUnifiDevices()
    refresh()
    
    //Logic to update Hubitat Code Base (generated by json file)
    CheckForUpdate()
    
    // Schedule checks that are only performed once a day
    // (not used at the moment)
    //schedule( "${ Second } ${ Minute } ${ Hour } ? * *", "DailyCheck" )
    
    // Schedule checks that are only performed once a day
    schedule( "${ Second } ${ Minute } ${ Hour } ? * *", "CheckForUpdate" )

    Logging( "Schedules Updated", 2 )

}

// refresh performs a poll of data
def refresh(){
    if(AqualinkdPort == null ){
        AqualinkdPort = "80"
        
    }
    RefreshAqualinkGeneralData()
    /*
    if( ShowAlarms ){
        CheckAlarms()
    }
    */
    ProcessEvent( "Last Refresh", new Date() )
}

// DailyCheck is for items that do not need to be "fresh" all the time
def DailyCheck(){
    ProcessState( "Driver Name", "${ DriverName() }" )
    ProcessState( "Driver Version", "${ DriverVersion() }" )
    Logging( "daily checks are ignored; WIP feature", 4 )
    //CallStatusAPI()
    //pauseExecution( 5000 )
    //RefreshAllClients()
    //RefreshUnifiDevices()
    refresh()
}

//Set Cookie for Inital API 
def setCookie( resp ){
    def Cookie
    try{
        resp.getHeaders().each{
            //if( ( it.value.split( '=' )[ 0 ].toString() == "aqualink" ) || ( it.value.split( '=' )[ 0 ].toString() == "TOKEN" ) )
            if (it.value.split( '=' )[ 0 ].toString() == "TOKEN" )
            {
                if (resp.getHeaders().'Set-Cookie' != null)
                {
                    Cookie = resp.getHeaders().'Set-Cookie'
                    ProcessState( "Cookie", Cookie )
                }
                else{
                     Logging( " cookie not found response ${ it.value }", 4 )

                }
            }
            else{
                Logging( "no cookie found in  response ${ it.value }", 4 )
                def CSRF
                if( CSRF.split( ':' )[ 0 ] == "X-CSRF-Token" )
                {
                    CSRF = it.value.split( ';' )[ 0 ].split( '=' )[ 1 ]
                    ProcessState( "CSRF", CSRF )
                }

            }
        }
    } catch( Exception e ){
        Logging( "Exception when performing setting cookie: ${ e }", 5 )
    }
}

//Call Status API for AqualinkD
def CallStatusAPI(){
    def Params
    Params = GenerateAPIParams("status")
    /*
    Params = [ uri: "http://${ AqualinkdURL }:${ AqualinkdPort }/api/status", 
                                ignoreSSLIssues: true, 
                                requestContentType: "application/json", 
                                contentType: "application/json", 
                                body: "" ]        
    */
    Logging( "Status Params: ${ Params }", 4 )
    //asynchttpPost( "ReceiveLogin", Params )
    try{
        httpPost( Params ){ resp ->
	        switch( resp.getStatus() ){
		        case 200:
                    Logging( "status response = ${ resp.data }", 4 )
                    ProcessEvent( "Status", "AqualinkD status successful" )
                    ProcessEvent( "Last Status", new Date() )
                    
                    //TODO get working if Cookie use
                    //setCookie(resp)
                    
                    // Resetting 1 minute refresh rate
                    def Second = ( new Date().format( "s" ) as int )
                    Second = ( (Second + 5) % 60 )
                    if( RefreshRate == "1 minute" ){ // Reschedule the refresh check if it is every minute
                        schedule( "${ Second } * * ? * *", "refresh" )
                    }
			        break
                case 408:
                    Logging( "Request Timeout to host: "+AqualinkdURL+" on port "+AqualinkdPort, 3 )
			        break
		        default:
			        Logging( "Error requesting AqualinkD status: ${ resp.status }", 4 )
			        break
	        }
        }
    } catch( Exception e ){
        Logging( "Exception when requesting aqualink status invocation: ${ e }", 5 )
    }
}

// Check if polling OK is a simple set of tests to make sure all needed information is available before polling
def PollingOK( Message = null ){
    def OK = false
    Logging( "polling logic not used; ignoring polling logic", 2 )
    return true
    // the code below is not used but a future example on a cookie check
    /*
    if( state.Site != null ){
        if( ( state.Cookie != null ) ){
            OK = true
        } else {
            Logging( "No Cookie available for authentication, first return status", 5 )
            ProcessEvent( "Status", "No Cookie, hit API." )
        }
    } else {
        Logging( "Site unknown, run RefreshAqualinkGeneralData or fill the Override Site preference.", 5 )
        ProcessEvent( "Status", "Site unknown, run RefreshAqualinkGeneralData or fill the Override Site preference." )
    }
    return OK
    */
}

//Poll Unifi for current account status
def CheckAccountStatus(){
    if( PollingOK() ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/self" ), [ Method: "CheckAccountStatus" ] )
    }
}

//Poll Unifi for connected clients data
def CheckClients(){
    if( PollingOK() ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/rest/user" ), [ Method: "CheckClients" ] )
    }
}

//Poll Unifi to list a specific Unifi device's data
def RefreshSpecificUnifiDevice( MAC = null ){
    if( PollingOK() ){
        if( MAC != null ){
            asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/stat/device/${ MAC }" ), [ Method: "RefreshSpecificUnifiDevice" ] )
        }
    }
}

//Poll Unifi to list all Unifi devices data
def RefreshUnifiDevices(){
    if( PollingOK() ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/stat/device" ), [ Method: "RefreshUnifiDevices" ] )
    }
}

//Poll to generate a simple list of Unifi devices
def RefreshUnifiDevicesBasic(){
    if( PollingOK() ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/stat/device-basic" ), [ Method: "RefreshUnifiDevicesBasic" ] )
    }
}

//Poll Unifi to list site sta
def RefreshOnlineClients(){
    if( PollingOK() ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/stat/sta" ), [ Method: "RefreshOnlineClients" ] )
    }
}

//Poll Unifi to list all clients ever
def RefreshAllClients(){
    if( PollingOK() ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/stat/alluser" ), [ Method: "RefreshAllClients" ] )
    }
}

// Gets the status output from aqualink
def RefreshAqualinkGeneralData(){
    //TODO :: Consider a Cookie or session check
    asynchttpGet( "ReceiveData", GenerateAPIParams( "status" ), [ Method: "RefreshAqualinkGeneralData" ] )

    /*
    if( ( state.Cookie != null ) ){
        asynchttpGet( "ReceiveData", GenerateAPIParams( "status" ), [ Method: "RefreshAqualinkGeneralData" ] )
        //asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/stat/sites" ), [ Method: "CurrentStats" ] )
    } else {
        Logging( "No Cookie set...", 5 )
    }
    */
}


// Configure child device settings based on Preferences
def SendChildSettings( String DNI, String ChildID, String Value ){
    def Attempt = "api/s/${ state.Site }/rest/device/"
    asynchttpPut( "ReceiveData", GenerateNetworkManageParams( "${ Attempt }", "${ ChildID }", "${ Value }" ), [ Method: "SendChildSettings", DNI: "${ DNI }", ChildID: "${ ChildID }", Value: "${ Value }" ] )
}
def GenerateAPIParams( String Path, String Data = null ){
    //TODO :: Future data null validation options
    // Example --
    //
    //        Params = [ uri: "https://${ AqualinkdURL }:{ AqualinkdPort }/api/${ Path }", 
    //                  ignoreSSLIssues: true, 
    //                  requestContentType: "application/json", 
    //                  contentType: "application/json", 
    //                  headers: [ Host: "${ AqualinkdURL }", 
    //                             Accept: "*/*", 
    //                             Cookie: "${ state.Cookie }" ], 
    //                  data:"${ Data }" ]

    if ( Data != null ) {
            Params = [ uri: "http://${ AqualinkdURL }:${ AqualinkdPort }/api/status", 
                                    contentType: "application/json", 
                                    headers: [ Host: "${ AqualinkdURL }", Accept: "*/*", Cookie: "${ state.Cookie }" ], 
                                    data:"${ Data }" ]       
        }
        else {
            Params = [ uri: "http://${ AqualinkdURL }:${ AqualinkdPort }/api/status", 
                                    contentType: "application/json" ]

        }
        Logging( "Parameters = ${ Params }", 4 )
	    return Params
}

// Generate Network Params assembles the parameters to be sent to the controller rather than repeat so much of it
def GenerateNetworkParams( String Path, String Data = null ){
    def Params = ""
    return Params
}

// Handles receiving the data from various commands
def ReceiveData( resp, data ){
	switch( resp.getStatus() ){
		case 200:
            Logging( "Received ${ resp.data }", 4 )
            def Json = parseJson( resp.data )
            ProcessEvent( "Status", "${ data.Method } successful." )
            switch( data.Method ){
                case "RefreshAqualinkGeneralData":
                    Logging( "Refreshing Aqualink general status data", 4 )
                    break
                /*
                case "CheckAccountStatus":
                    def SiteNumber = 0
                    Json.data.each(){
                        if( SiteNumber == 0 ){
                            ProcessState( "Site ID", it.site_id )
                            ProcessState( "Site Name", it.site_name )
                        } else {
                            ProcessState( "Site ${ ( SiteNumber + 2 ) } ID", it.site_id )
                            ProcessState( "Site ${ ( SiteNumber + 2 ) } Name", it.site_name )
                        }
                        SiteNumber = SiteNumber + 1
                    }
                    break
                case "RefreshOnlineClients":
                    ProcessEvent( "Online Clients", Json.data.size() )
                    break
                case "RefreshAllClients":
                case "CheckClients":
                    ProcessEvent( "Total Clients", Json.data.size() )
                    break
                case "RefreshSpecificUnifiDevice":
                case "RefreshUnifiDevicesBasic":
                case "RefreshUnifiDevices":
                    if( data.Method != "RefreshSpecificUnifiDevice" ){
                        ProcessEvent( "Unifi Devices", Json.data.size() )
                    }
                    if( UnifiChildren ){
                        Json.data.each(){
                            if( it.model != null ){
                                switch( it.model ){
                                    case "US48PRO": // Unifi 48 Port PRO PoE Switch (USW48PoE)
                                        if( getChildDevice( "USW48PoE ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW48PoE ${ it.mac }", "Device Type", "USW48PoE" )
                                        }
                                        ProcessData( "USW48PoE ${ it.mac }", it )
                                        break
                                    default:
                                        Logging( "Unidentified Unifi ${ it.model } Device = ${ it }", 3 )
                                        if( getChildDevice( "Generic ${ it.mac }" ) == null ){
                                            PostEventToChild( "Generic ${ it.mac }", "Device Type", "Generic" )
                                        }
                                        ProcessData( "Generic ${ it.mac }", it )
                                        break
                                }
                            }
                        }
                    }
                    break
                case "PresenceCheck":
                case "PowerOutlet":
                case "SendChildSettings":
                    if( UnifiChildren ){
                        if( Json.data != null ){
                            if( Json.data[ 0 ] != null ){
                                if( Json.data[ 0 ].size() > 0 ){
                                    ProcessData( "${ data.DNI }", Json.data[ 0 ] )
                                } else {
                                    Logging( "No data returned, no changes made on controller side.", 4 )
                                }
                            } else {
                                Logging( "No data returned, no changes made on controller side.", 4 )
                            }
                        } else {
                            Logging( "No data returned, no changes made on controller side.", 4 )
                        }
                    }
                    break
                    */
                default:
                    Logging( "Received Data for ${ data.Method }: ${ resp.data }", 3 )
                    /*
                    if( UnifiChildren ){
                        if( Json.data[ 0 ].size() > 0 ){
                            ProcessData( "${ data.DNI }", Json.data[ 0 ] )
                        } else {
                            Logging( "${ data.Method } attempt failed.", 5 )
                        }
                    }
                    */
			        break
            }
            break
        case 400: // Bad request
            switch( data.Method ){
                // Process a 400 Response 
                case "PresenceCheck":
                case "ClientCheck":
                    Logging( "CheckClient ${ data.ClientMAC } = Offline", 4 )
                    break
                default:
                    Logging( "Bad Request for ${ data.Method }", 3 )
			        break
            }
            break
        case 401:
            ProcessEvent( "Status", "${ data.Method } Unauthorized, please validate connection" )
            Logging( "Unauthorized for ${ data.Method } please validate connection", 5 )
			break
        case 404:
            ProcessEvent( "Status", "${ data.Method } Page not found error" )
            Logging( "Page not found for ${ data.Method }", 5 )
			break
        case 408:
            ProcessEvent( "Status", "Request timeout for ${ data.Method }" )
		default:
            ProcessEvent( "Status", "Error ${ resp.status } connecting for ${ data.Method }" )
			Logging( "Error connecting to AqualinkD: ${ resp.status } for ${ data.Method }", 5 )
			break
	}
}

// Process data coming in for a device
def ProcessData( String Device, data ){
    Logging( "${ Device } Data: ${ data }", 4 )
}

// Pings a specific IP address using the Hubitat not the Aqualink
def Ping( IP ){
    def TempVersion
    if( location.hub.firmwareVersionString != null ){
        TempVersion = location.hub.firmwareVersionString
        TempVersion = TempVersion.split( /\./ )
        if( TempVersion[ 0 ] >= 2 && TempVersion[ 1 ] >= 2 && TempVersion[ 2 ] >= 7 ){
            ProcessEvent( "Ping Result", "Ping for ${ IP }: ${ hubitat.helper.NetworkUtils.ping( IP ) }" )
        } else {
            Logging( "Ping feature requires Hubitat hub version of 2.2.7 or greater.", 2 )
        }
    } else {
        Logging( "Ping feature requires Hubitat hub version of 2.2.7 or greater.", 2 )
    }
}

// installed is called when the device is installed
def installed(){
	Logging( "Installed", 2 )
}

// initialize is called when the device is initialized
def initialize(){
	Logging( "Initialized", 2 )
}

// uninstalling device so make sure to clean up children
void uninstalled() {
    // Delete all children
    getChildDevices().each{
        deleteChildDevice( it.deviceNetworkId )
    }
    unschedule()
    Logging( "Uninstalled", 2 )
}

// parse appears to be one of those "special" methods for when data is returned 
def parse( String description ){
    Logging( "Parse = ${ description }", 3 )
}

// Used to convert epoch values to text dates
def String ConvertEpochToDate( String Epoch ){
    Long Temp = Epoch.toLong()
    def date
    if( Temp <= 9999999999 ){
        date = new Date( ( Temp * 1000 ) ).toString()
    } else {
        date = new Date( Temp ).toString()
    }
    return date
}

// Checks the location.getTemperatureScale() to convert temperature values
def ConvertTemperature( String Scale, Number Value ){
    if( Value != null ){
        def ReturnValue = Value as double
        if( location.getTemperatureScale() == "C" && Scale.toUpperCase() == "F" ){
            ReturnValue = ( ( ( Value - 32 ) * 5 ) / 9 )
            Logging( "Temperature Conversion ${ Value }°F to ${ ReturnValue }°C", 4 )
        } else if( location.getTemperatureScale() == "F" && Scale.toUpperCase() == "C" ) {
            ReturnValue = ( ( ( Value * 9 ) / 5 ) + 32 )
            Logging( "Temperature Conversion ${ Value }°C to ${ ReturnValue }°F", 4 )
        } else if( ( location.getTemperatureScale() == "C" && Scale.toUpperCase() == "C" ) || ( location.getTemperatureScale() == "F" && Scale.toUpperCase() == "F" ) ){
            ReturnValue = Value
        }
        def TempInt = ( ReturnValue * 100 ) as int
        ReturnValue = ( TempInt / 100 )
        return ReturnValue
    }
}

// Process data to check against current state value and then send an event if it has changed
def ProcessEvent( Variable, Value, Unit = null ){
    if( state."${ Variable }" != Value ){
        state."${ Variable }" = Value
        if( Unit != null ){
            Logging( "Event: ${ Variable } = ${ Value }${ Unit }", 4 )
            sendEvent( name: "${ Variable }", value: Value, unit: Unit, isStateChanged: true )
        } else {
            Logging( "Event: ${ Variable } = ${ Value }", 4 )
            sendEvent( name: "${ Variable }", value: Value, isStateChanged: true )
        }
    }
    else {
         Logging( "No change for Event: ${ Variable } = ${ Value }", 4 )
    }
}

// Process data to check against current state value and then send an event if it has changed
def ProcessState( Variable, Value ){
    if( state."${ Variable }" != Value ){
        Logging( "State: ${ Variable } = ${ Value }", 4 )
        state."${ Variable }" = Value
    }
}

// Post data to child device
def PostEventToChild( Child, Variable, Value, Unit = null ){
    if( "${ Child }" != null ){
        log.info("Future update will add child items for "+Child)
        if( getChildDevice( "${ Child }" ) == null ){
            TempChild = Child.split( " " )
            def ChildType = ""
            /*
            switch( TempChild[ 0 ] ){
                case "Presence":
                    ChildType = "Presence"
                    break
                default:
                    ChildType = "Generic"
                    break
            }
            */
            //addChild( "${ Child }", ChildType )
        }
        // Add processing for children above (if needed)
        if( getChildDevice( "${ Child }" ) != null ){
            if( Unit != null ){
                //getChildDevice( "${ Child }" ).ProcessEvent( "${ Variable }", Value, "${ Unit }" )
                Logging( "${ Child } Event: ${ Variable } = ${ Value }${ Unit }", 4 )
            } else {
                //getChildDevice( "${ Child }" ).ProcessEvent( "${ Variable }", Value )
                Logging( "${ Child } Event: ${ Variable } = ${ Value }", 4 )
            }
        } else {
            if( Unit != null ){
                Logging( "Failure to add ${ Child } and post ${ Variable }=${ Value }${ Unit }", 5 )
            } else {
                Logging( "Failure to add ${ Child } and post ${ Variable }=${ Value }", 5 )
            }
        }
    } else {
        Logging( "Failure to add child because child name was null", 5 )
    }
}

// Post data to child device
def PostStateToChild( Child, Variable, Value ){
    if( "${ Child }" != null ){
       log.info("Future update will add child items")
        if( getChildDevice( "${ Child }" ) == null ){
            TempChild = Child.split( " " )
            def ChildType = ""
            /*
            switch( TempChild[ 0 ] ){
                case "Presence":
                    ChildType = "Presence"
                    break
                case "Plug":
                    ChildType = "Plug"
                    break
                default:
                    ChildType = "Generic"
                    break
            
            }
            */
            //addChild( "${ Child }", ChildType )
        }
        if( getChildDevice( "${ Child }" ) != null ){
            Logging( "${ Child } State: ${ Variable } = ${ Value }", 4 )
            //getChildDevice( "${ Child }" ).ProcessState( "${ Variable }", Value )
        } else {
            Logging( "Failure to add ${ Child } and post ${ Variable }=${ Value }", 5 )
        }
    } else {
        Logging( "Failure to add child because child name was null", 5 )
    }
}

// Adds a Aqualinkd Children
// Based on @mircolino's method for child sensors
def addChild( String DNI, String ChildType ){
    try{
        Logging( "addChild(${ DNI })", 3 )
        log.info("Future update will add child item type "+ChildType)
        /*
        if( UnifiChildren ){
            switch( ChildType ){
                case "Presence":
                    addChildDevice( "UnifiNetworkChild-Presence", DNI, [ name: "${ DNI }" ] )
                    break
                case "Plug":
                    addChildDevice( "UnifiNetworkChild-Plug", DNI, [ name: "${ DNI }" ] )
                    break
                default:
                    addChildDevice( "UnifiNetworkChild", DNI, [ name: "${ DNI }" ] )
                    break
            }
        } else {
            addChildDevice( "UnifiNetworkChild-Presence", DNI, [ name: "${ DNI }" ] )
        }
        */
    }
    catch( Exception e ){
        def Temp = e as String
        if( Temp.contains( "not found" ) ){
            Logging( "AqualinkDChild-${ ChildType } driver is not loaded for ${ DNI }.", 5 )
        } else {
            Logging( "addChild error, likely ${ DNI } already exists: ${ Temp }", 5 )
        }
    }
}

// Handles whether logging is enabled and thus what to put there.
def Logging( LogMessage, LogLevel ){
	// Add all messages as info logging
    if( ( LogLevel == 2 ) && ( LogType != "None" ) ){
        log.info( "${ device.displayName } - ${ LogMessage }" )
    } else if( ( LogLevel == 3 ) && ( ( LogType == "Debug" ) || ( LogType == "Trace" ) ) ){
        log.debug( "${ device.displayName } - ${ LogMessage }" )
    } else if( ( LogLevel == 4 ) && ( LogType == "Trace" ) ){
        log.trace( "${ device.displayName } - ${ LogMessage }" )
    } else if( LogLevel == 5 ){
        log.error( "${ device.displayName } - ${ LogMessage }" )
    }
}

// Checks drdsnell.com for the latest version of the driver
// Original inspiration from @cobra's version checking
def CheckForUpdate(){
    ProcessEvent( "Driver Name", DriverName() )
    ProcessEvent( "Driver Version", DriverVersion() )
    log.warn("Future update will poll for new changes")
    /*
	httpGet( uri: "https://www.drdsnell.com/projects/hubitat/drivers/versions.json", contentType: "application/json" ){ resp ->
        switch( resp.status ){
            case 200:
                if( resp.data."${ DriverName() }" ){
                    CurrentVersion = DriverVersion().split( /\./ )
                    if( resp.data."${ DriverName() }".version == "REPLACED" ){
                       ProcessEvent( "Driver Status", "Driver replaced, please use ${ resp.data."${ state.'Driver Name' }".file }" )
                    } else if( resp.data."${ DriverName() }".version == "REMOVED" ){
                       ProcessEvent( "Driver Status", "Driver removed and no longer supported." )
                    } else {
                        SiteVersion = resp.data."${ DriverName() }".version.split( /\./ )
                        if( CurrentVersion == SiteVersion ){
                            Logging( "Driver version up to date", 3 )
				            ProcessEvent( "Driver Status", "Up to date" )
                        } else if( ( CurrentVersion[ 0 ] as int ) > ( SiteVersion [ 0 ] as int ) ){
                            Logging( "Major development ${ CurrentVersion[ 0 ] }.${ CurrentVersion[ 1 ] }.${ CurrentVersion[ 2 ] } version", 3 )
				            ProcessEvent( "Driver Status", "Major development ${ CurrentVersion[ 0 ] }.${ CurrentVersion[ 1 ] }.${ CurrentVersion[ 2 ] } version" )
                        } else if( ( CurrentVersion[ 1 ] as int ) > ( SiteVersion [ 1 ] as int ) ){
                            Logging( "Minor development ${ CurrentVersion[ 0 ] }.${ CurrentVersion[ 1 ] }.${ CurrentVersion[ 2 ] } version", 3 )
				            ProcessEvent( "Driver Status", "Minor development ${ CurrentVersion[ 0 ] }.${ CurrentVersion[ 1 ] }.${ CurrentVersion[ 2 ] } version" )
                        } else if( ( CurrentVersion[ 2 ] as int ) > ( SiteVersion [ 2 ] as int ) ){
                            Logging( "Patch development ${ CurrentVersion[ 0 ] }.${ CurrentVersion[ 1 ] }.${ CurrentVersion[ 2 ] } version", 3 )
				            ProcessEvent( "Driver Status", "Patch development ${ CurrentVersion[ 0 ] }.${ CurrentVersion[ 1 ] }.${ CurrentVersion[ 2 ] } version" )
                        } else if( ( SiteVersion[ 0 ] as int ) > ( CurrentVersion[ 0 ] as int ) ){
                            Logging( "New major release ${ SiteVersion[ 0 ] }.${ SiteVersion[ 1 ] }.${ SiteVersion[ 2 ] } available", 2 )
				            ProcessEvent( "Driver Status", "New major release ${ SiteVersion[ 0 ] }.${ SiteVersion[ 1 ] }.${ SiteVersion[ 2 ] } available" )
                        } else if( ( SiteVersion[ 1 ] as int ) > ( CurrentVersion[ 1 ] as int ) ){
                            Logging( "New minor release ${ SiteVersion[ 0 ] }.${ SiteVersion[ 1 ] }.${ SiteVersion[ 2 ] } available", 2 )
				            ProcessEvent( "Driver Status", "New minor release ${ SiteVersion[ 0 ] }.${ SiteVersion[ 1 ] }.${ SiteVersion[ 2 ] } available" )
                        } else if( ( SiteVersion[ 2 ] as int ) > ( CurrentVersion[ 2 ] as int ) ){
                            Logging( "New patch ${ SiteVersion[ 0 ] }.${ SiteVersion[ 1 ] }.${ SiteVersion[ 2 ] } available", 2 )
				            ProcessEvent( "Driver Status", "New patch ${ SiteVersion[ 0 ] }.${ SiteVersion[ 1 ] }.${ SiteVersion[ 2 ] } available" )
                        }
                    }
                } else {
                    Logging( "${ DriverName() } is not published on drdsnell.com", 2 )
                    ProcessEvent( "Driver Status", "${ DriverName() } is not published on drdsnell.com" )
                }
                break
            default:
                Logging( "Unable to check drdsnell.com for ${ DriverName() } driver updates.", 2 )
                break
        }
    }
    */
}