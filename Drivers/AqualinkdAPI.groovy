/*
* AqualinkdAPI
*
* Description:
* This Hubitat driver allows polling of the AqualinkD API
*
* Overall Setup:
* 1) Add the AqualinkdAPI.groovy a
* 2) Add a Virtual Device(s)  :: TODO Task
*
* Licensing:
* Copyright 2023 Casey Cruise
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
* on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
* for the specific language governing permissions and limitations under the License.
*
* Known Issue(s):
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
        command "LogCallStatusAPI" // Logs in to the controller to get a cookie for the session
   
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
     httpGet( uri: "http://192.168.11.121/api/status", contentType: "application/json" ){ resp ->
        switch( resp.status ){
            case 200:
                if( resp.data."${ AqualinkDVerName() }" ){
                    StatusVerDaemonAqualink = resp.data."${ AqualinkDVerName() }"
                    Logging( "AqualinkD Status set to "+StatusVerDaemonAqualink, 2 )
                    ProcessEvent( "AqualinkD Version", StatusVerDaemonAqualink )
                    ProcessState( "AqualinkD Version", StatusVerDaemonAqualink )

                    //Test Setting AquaLinkD Values
                     def setAlDate = resp.data."${ AqualinkDDate()  }"
                     def setAlTime = resp.data."${ AqualinkDTime() }"
                     def setAlTempUnit = resp.data."${ AqualinkDTempUnits() }"
                     def setFrzPrtStatus = resp.data."${ AqualinkDFrzProtectStatus() }"

                     AqualinkDFrzProtectStatus 
                    Logging( "AqualinkD date set to "+setAlDate, 2 )
                    Logging( "AqualinkD time set to "+setAlTime, 2 )
                    Logging( "AqualinkD temp unit set to "+setAlTempUnit, 2 )
                    Logging( "AqualinkD freeze prtct status set to "+setFrzPrtStatus, 2 )


 

                   
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
    
    
    
    // Deal with SiteOverride before getting CurrentStats
    /*
    if( SiteOverride != null ){
        ProcessEvent( "Site", "${ SiteOverride }" )
    } else { // Needed to add in a default value here because controller does not respond fast enough
        state.Site = "default"
    }
    */
    // Attempt to call status API
    CallStatusAPI()
     
    //RefreshAllClients()
    //RefreshUnifiDevices()
    refresh()
    CheckForUpdate()
    
    // Schedule checks that are only performed once a day
    schedule( "${ Second } ${ Minute } ${ Hour } ? * *", "DailyCheck" )
    
    // Schedule checks that are only performed once a day
    schedule( "${ Second } ${ Minute } ${ Hour } ? * *", "CheckForUpdate" )
    
    // Setup scheduling for presence checking if any MAC address is entered
    /*
    if( MACPresence != null ){
        Logging( "MACPresense List size = ${ MACPresence.split( ";").size() }", 4 )
        if( MACPresence.split( ";").size() <= 5 ){
            schedule( "${ Second } * * * * ?", PresenceCheck )
        } else {
            if( MACPresence.split( ";").size() <= 10 ){
                schedule( "${ Second } 0/2 * * * ?", PresenceCheck )
            } else {
                ProcessEvent( "Status", "Must be 10 or less MAC addresses for Presence Checking.", 5 )
                Logging( "Too many MAC addresses to check for presence regularly.", 5 )
            }
        }
    }
    Logging( "Updated", 2 )
    */
}

// refresh performs a poll of data
def refresh(){
    if(AqualinkdPort == null ){
        AqualinkdPort = "80"
        
    }
    //CurrentStats()
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
    CallStatusAPI()
    pauseExecution( 5000 )
    //RefreshAllClients()
    //RefreshUnifiDevices()
    refresh()
}

//Call Status API for AqualinkD
def CallStatusAPI(){
    def Params
    Params = [ uri: "http://${ AqualinkdURL }:${ AqualinkdPort }/api/status", 
                                ignoreSSLIssues: true, 
                                requestContentType: "application/json", 
                                contentType: "application/json", 
                                body: "" ]        
    Logging( "Status Params: ${ Params }", 4 )
    asynchttpPost( "ReceiveLogin", Params )
    try{
        httpPost( Params ){ resp ->
	        switch( resp.getStatus() ){
		        case 200:
                    Logging( "Login response = ${ resp.data }", 4 )
                    ProcessEvent( "Status", "AqualinkD status successful" )
                    ProcessEvent( "Last Status", new Date() )
                    def Cookie
                    resp.getHeaders().each{
                        if( ( it.value.split( '=' )[ 0 ].toString() == "unifises" ) || ( it.value.split( '=' )[ 0 ].toString() == "TOKEN" ) ){
                            Cookie = resp.getHeaders().'Set-Cookie'
                            //if( Controller == "Unifi Dream Machine (inc Pro)" ){
                            //    Cookie = Cookie.split( ";" )[ 0 ] + ";"
                            //} else {
                            //    Cookie = Cookie.split( ";" )[ 0 ]
                            //}
                            ProcessState( "Cookie", Cookie )
                        } else {
                            def CSRF
                            CSRF = it.value.split( ';' )[ 0 ].split( '=' )[ 1 ]
                            ProcessState( "CSRF", CSRF )
                            //if( Controller == "Unifi Dream Machine (inc Pro)" ){
                            //    CSRF = it as String
                            //    if( CSRF.split( ':' )[ 0 ] == "X-CSRF-Token" ){
                            //       ProcessState( "CSRF", it.value )
                            //    }
                            //} else {
                            //    if( it.value.split( '=' )[ 0 ].toString() == "csrf_token" ){
                            //        CSRF = it.value.split( ';' )[ 0 ].split( '=' )[ 1 ]
                            //        ProcessState( "CSRF", CSRF )
                            //    }
                        }
                    }
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
        Logging( "Exception when performing Login: ${ e }", 5 )
    }
}

// Check if polling OK is a simple set of tests to make sure all needed information is available before polling
def PollingOK( Message = null ){
    def OK = false
    if( state.Site != null ){
        if( ( state.Cookie != null ) ){
            OK = true
        } else {
            Logging( "No Cookie available for authentication, must Login", 5 )
            ProcessEvent( "Status", "No Cookie, please Login again." )
        }
    } else {
        Logging( "Site unknown, run CurrentStats or fill the Override Site preference.", 5 )
        ProcessEvent( "Status", "Site unknown, run CurrentStats or fill the Override Site preference." )
    }
    return OK
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

// Gets the statistics for the current sites
def CurrentStats(){
    if( ( state.Cookie != null ) ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/stat/sites" ), [ Method: "CurrentStats" ] )
    } else {
        Logging( "No Cookie set...", 5 )
    }
}

// Gets the health for the site
def CurrentHealth(){
    if( PollingOK() ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/s/${ state.Site }/stat/health" ), [ Method: "CurrentHealth" ] )
    }
}

// Gets basic stats for the sites
def CurrentSites(){
    if( ( state.Cookie != null ) ){
        asynchttpGet( "ReceiveData", GenerateNetworkParams( "api/self/sites" ), [ Method: "CurrentSites" ] )
    } else {
        Logging( "No Cookie available for authentication, must Login", 5 )
    }
}


// Configure child device settings based on Preferences
def SendChildSettings( String DNI, String ChildID, String Value ){
    def Attempt = "api/s/${ state.Site }/rest/device/"
    asynchttpPut( "ReceiveData", GenerateNetworkManageParams( "${ Attempt }", "${ ChildID }", "${ Value }" ), [ Method: "SendChildSettings", DNI: "${ DNI }", ChildID: "${ ChildID }", Value: "${ Value }" ] )
}

// Generate Network Params assembles the parameters to be sent to the controller rather than repeat so much of it
def GenerateNetworkParams( String Path, String Data = null ){
	def Params
	if( Controller == "Unifi Dream Machine (inc Pro)" ){
        if( Data != null ){
            Params = [ uri: "https://${ AqualinkdURL }:443/proxy/network/${ Path }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Accept: "*/*", Cookie: "${ state.Cookie }" ], data:"${ Data }" ]
        } else {
            Params = [ uri: "https://${ AqualinkdURL }:443/proxy/network/${ Path }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Accept: "*/*", Cookie: "${ state.Cookie }" ] ]
        }
	} else {
        if( Data != null ){
            Params = [ uri: "https://${ AqualinkdURL }:${ AqualinkdPort }/${ Path }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Accept: "*/*", Cookie: "${ state.Cookie }" ], data:"${ Data }" ]
        } else {
            Params = [ uri: "https://${ AqualinkdURL }:${ AqualinkdPort }/${ Path }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Accept: "*/*", Cookie: "${ state.Cookie }" ] ]
        }
	}
    //Logging( "Parameters = ${ Params }", 4 )
	return Params
}

// GenerateNetworkCommandParams assembles the parameters to be sent to the controller rather than repeat so much of it
def GenerateNetworkCommandParams( String Path, String ChildID, String Data = null ){
	def Params
	if( Controller == "Unifi Dream Machine (inc Pro)" ){
        if( Data != null ){
            Params = [ uri: "https://${ AqualinkdURL }/proxy/network/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/${ ChildID }/general", Origin: "https://${ AqualinkdURL }", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ], body:"${ Data }" ]
        } else {
            Params = [ uri: "https://${ AqualinkdURL }/proxy/network/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/${ ChildID }/general", Origin: "https://${ AqualinkdURL }", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ] ]
        }
	} else {
        if( Data != null ){
            Params = [ uri: "https://${ AqualinkdURL }:${ AqualinkdPort }/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/${ ChildID }/general", Origin: "https://${ AqualinkdURL }", Accept: "*/*", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ], body:"${ Data }" ]
        } else {
            Params = [ uri: "https://${ AqualinkdURL }:${ AqualinkdPort }/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/${ ChildID }/general", Origin: "https://${ AqualinkdURL }", Accept: "*/*", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ] ]
        }
	}
    Logging( "Parameters = ${ Params }", 4 )
	return Params
}

// GenerateNetworkManageParams assembles the parameters to be sent to the controller rather than repeat so much of it
def GenerateNetworkManageParams( String Path, String ChildID, String Data ){
	def Params
	if( Controller == "Unifi Dream Machine (inc Pro)" ){
        if( Data != null ){
            Params = [ uri: "https://${ AqualinkdURL }/proxy/network/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/properties/${ ChildID }/device", Origin: "https://${ AqualinkdURL }", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ], body:"${ Data }" ]
        } else {
            Params = [ uri: "https://${ AqualinkdURL }:${ AqualinkdPort }/proxy/network/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/properties/${ ChildID }/device", Origin: "https://${ AqualinkdURL }", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ] ]
        }
	}
    Logging( "Parameters = ${ Params }", 4 )
	return Params
}

// GenerateNetworkSettingParams assembles the parameters to be sent to the controller rather than repeat so much of it
def GenerateNetworkSettingParams( String Path, String ChildID, String MAC, String Data ){
	def Params
	if( Controller == "Unifi Dream Machine (inc Pro)" ){
        if( Data != null ){
            Params = [ uri: "https://${ AqualinkdURL }/proxy/network/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/properties/${ MAC }/settings", Origin: "https://${ AqualinkdURL }", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ], body:"${ Data }" ]
        } else {
            Params = [ uri: "https://${ AqualinkdURL }:${ AqualinkdPort }/proxy/network/${ Path }/${ ChildID }", ignoreSSLIssues: true, requestContentType: "application/json", contentType: "application/json", headers: [ Host: "${ AqualinkdURL }", Referer: "https://${ AqualinkdURL }/network/${ state.Site }/devices/properties/${ MAC }/settings", Origin: "https://${ AqualinkdURL }", Cookie: "${ state.Cookie }", 'X-CSRF-Token': "${ state.CSRF }" ] ]
        }
	}
    Logging( "Parameters = ${ Params }", 4 )
	return Params
}

// Run a command on the controller
def RunDevMgrCommand( String Method, String MAC, String Data ){
    if( PollingOK() ){
        if( MAC != null ){
            def Params
            if( Controller == "Unifi Dream Machine (inc Pro)" ){
                Params = [ uri: "https://${ AqualinkdURL }:443/proxy/network/api/s/${ state.Site }/cmd/devmgr", ignoreSSLIssues: true, headers: [ Host: "${ AqualinkdURL }:443", Accept: "*/*", Cookie: "${ state.Cookie }", "X-CSRF-Token": "${ state.CSRF }" ], body: "${ Data }" ]
            } else {
                Params = [ uri: "https://${ AqualinkdURL }:${ AqualinkdPort }/api/s/${ state.Site }/cmd/devmgr", ignoreSSLIssues: true, headers: [ Host: "${ AqualinkdURL }:${ AqualinkdPort }", Accept: "*/*", Cookie: "${ state.Cookie }", "X-CSRF-Token": "${ state.CSRF }" ], body: "${ Data }" ]
            }
            Logging( "${ Method } Params = ${ Params }", 4 )
            try{
                httpPost( Params ){ resp ->
                    switch( resp.getStatus() ){
                        case 200:
                            ProcessEvent( "Status", "${ Method } ${ MAC }" )
                            Logging( "${ Method } succeeded = ${ resp.data }", 4 )
                            break
                        default:
                            Logging( "${ Method } received ${ resp.getStatus() }", 3 )
                            break
                    }
                }
            } catch( Exception e ){
                Logging( "${ Method } failed due to ${ e }", 5 )
            }
        } else {
            Logging( "No MAC address entered.", 3 )
            ProcessEvent( "Status", "No MAC address entered." )
        }
    }
}

// Handles receiving the data from various commands
def ReceiveData( resp, data ){
	switch( resp.getStatus() ){
		case 200:
            Logging( "Received ${ resp.data }", 4 )
            def Json = parseJson( resp.data )
            ProcessEvent( "Status", "${ data.Method } successful." )
            switch( data.Method ){
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
                                    case "US48": // Unifi 48 Port non-PoE Switch (USW48)
                                        if( getChildDevice( "USW48 ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW48 ${ it.mac }", "Device Type", "USW48" )
                                        }
                                        ProcessData( "USW48 ${ it.mac }", it )
                                        break
                                    case "USL24P250": // Unifi 24 Port 250W PoE Switch (USW24PoE)
                                    case "USL24P": // Unifi 24 Port PoE Switch (USW24PoE)
                                    case "US624P": // Unifi 24 Port Enterprise PoE Switch (USW24PoE)
                                        if( getChildDevice( "USW24PoE ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW24PoE ${ it.mac }", "Device Type", "USW24PoE" )
                                        }
                                        ProcessData( "USW24PoE ${ it.mac }", it )
                                        break
                                    case "US24": // Unifi 24 Port non-PoE Switch (USW24)
                                    case "USL24": // Unifi 24 Port non-PoE Switch (USW24)
                                        if( getChildDevice( "USW24 ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW24 ${ it.mac }", "Device Type", "USW24" )
                                        }
                                        ProcessData( "USW24 ${ it.mac }", it )
                                        break
                                    case "US16P150": // Unifi 16 Port 150W PoE Switch (USW16PoE)
                                        if( getChildDevice( "USW16PoE ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW16PoE ${ it.mac }", "Device Type", "USW16PoE" )
                                        }
                                        ProcessData( "USW16PoE ${ it.mac }", it )
                                        break
                                    case "USL16LP": // Unifi 16 Port Lite PoE Switch (USW16LPoE)
                                    case "USL16P": // Unifi 16 Port Lite PoE Switch (USW16LPoE)
                                        if( getChildDevice( "USW16LPoE ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW16LPoE ${ it.mac }", "Device Type", "USW16LPoE" )
                                        }
                                        ProcessData( "USW16LPoE ${ it.mac }", it )
                                        break
                                    case "US16": // Unifi 16 Port non-PoE Switch (USW16)
                                    case "USL16": // Unifi 16 Port non-PoE Switch (USW16)
                                        if( getChildDevice( "USW16 ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW16 ${ it.mac }", "Device Type", "USW16" )
                                        }
                                        ProcessData( "USW16 ${ it.mac }", it )
                                        break
                                    case "US8P150": // Unifi 8 Port 150W PoE Switch (USW8PoE)
                                        if( getChildDevice( "USW8PoE ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW8PoE ${ it.mac }", "Device Type", "USW8PoE" )
                                        }
                                        ProcessData( "USW8PoE ${ it.mac }", it )
                                        break
                                    case "US8P60": // Unifi 8 Port 60W PoE Switch (USW8PoE)
                                        if( getChildDevice( "USW8PoE60 ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW8PoE60 ${ it.mac }", "Device Type", "USW8PoE60" )
                                        }
                                        ProcessData( "USW8PoE60 ${ it.mac }", it )
                                        break
                                    case "USL8LP": // Unifi Lite 8 Port PoE Switch (USW-Lite-8-PoE)
                                        if( getChildDevice( "USW8LPoE ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW8LPoE ${ it.mac }", "Device Type", "USW8LPoE" )
                                        }
                                        ProcessData( "USW8LPoE ${ it.mac }", it )
                                        break
                                    case "US8": // Unifi 8 Port non-PoE Switch (USW8)
                                    case "USL8": // Unifi 8 Port non-PoE Switch (USW8)
                                        if( getChildDevice( "USW8 ${ it.mac }" ) == null ){
                                            PostEventToChild( "USW8 ${ it.mac }", "Device Type", "USW8" )
                                        }
                                        ProcessData( "USW8 ${ it.mac }", it )
                                        break
                                    case "UHDIW": // Unifi HD In Wall
                                        if( getChildDevice( "UHDIW ${ it.mac }" ) == null ){
                                            PostEventToChild( "UHDIW ${ it.mac }", "Device Type", "UHDIW" )
                                        }
                                        ProcessData( "UHDIW ${ it.mac }", it )
                                        break
                                    case "U7NHD": // Unifi Access Point nanoHD
                                    case "UAP6MP": // Unifi Access Point 6 Pro
                                    case "UALR6v2": // Unifi Access Point 6 LR
                                    case "UAL6": // Unifi Access Point 6 Lite
                                    case "U7P": // Unifi AC Pro
                                    case "U7MP": // UAP-AC-M-Pro
                                    case "U7PG2": // UAP-AC-Pro
                                    case "U7LT": // UAP-AC-Lite
                                    case "U7HD": // UAP-AC-HD
                                    case "UCXG": // UAP-XG
                                    case "BZ2LR": // Unifi AC-LR
                                    case "U6M": // Unifi U6 Mesh
                                        if( getChildDevice( "AP ${ it.mac }" ) == null ){
                                            PostEventToChild( "AP ${ it.mac }", "Device Type", "AP" )
                                        }
                                        ProcessData( "AP ${ it.mac }", it )
                                        break
                                    case "U7MSH": // UAP-AC-M
                                        if( getChildDevice( "BasicAP ${ it.mac }" ) == null ){
                                            PostEventToChild( "BasicAP ${ it.mac }", "Device Type", "BasicAP" )
                                        }
                                        ProcessData( "BasicAP ${ it.mac }", it )
                                        break
                                    case "USPRPS": // Unifi Redundant Power System
                                        if( getChildDevice( "RPS ${ it.mac }" ) == null ){
                                            PostEventToChild( "RPS ${ it.mac }", "Device Type", "RPS" )
                                        }
                                        ProcessData( "RPS ${ it.mac }", it )
                                        break
                                    case "UDMPROSE": // Unifi Dream Machine Pro SE
                                        if( getChildDevice( "UDMPSE ${ it.mac }" ) == null ){
                                            PostEventToChild( "UDMPSE ${ it.mac }", "Device Type", "UDMPSE" )
                                        }
                                        ProcessData( "UDMPSE ${ it.mac }", it )
                                        break
                                    case "UDMPRO": // Unifi Dream Machine Pro
                                        if( getChildDevice( "UDMP ${ it.mac }" ) == null ){
                                            PostEventToChild( "UDMP ${ it.mac }", "Device Type", "UDMP" )
                                        }
                                        ProcessData( "UDMP ${ it.mac }", it )
                                        break
                                    case "UDM": // Unifi Dream Machine
                                        if( getChildDevice( "UDM ${ it.mac }" ) == null ){
                                            PostEventToChild( "UDM ${ it.mac }", "Device Type", "UDM" )
                                        }
                                        ProcessData( "UDM ${ it.mac }", it )
                                        break
                                    case "UP1": // Unifi Smart Plug
                                        if( getChildDevice( "Plug ${ it.mac }" ) == null ){
                                            PostEventToChild( "Plug ${ it.mac }", "Device Type", "Plug" )
                                        }
                                        ProcessData( "Plug ${ it.mac }", it )
                                        break
                                    case "UP6": // Unifi Power Strip
                                        if( getChildDevice( "UP6 ${ it.mac }" ) == null ){
                                            PostEventToChild( "UP6 ${ it.mac }", "Device Type", "UP6" )
                                        }
                                        ProcessData( "UP6 ${ it.mac }", it )
                                        break
                                    case "USPPDUP": // Unifi SmartPower Pro PDU
                                        if( getChildDevice( "USPPDUP ${ it.mac }" ) == null ){
                                            PostEventToChild( "USPPDUP ${ it.mac }", "Device Type", "USPPDUP" )
                                        }
                                        ProcessData( "USPPDUP ${ it.mac }", it )
                                        break
                                    case "USMINI": // Unifi Switch Mini
                                        if( getChildDevice( "USMINI ${ it.mac }" ) == null ){
                                            PostEventToChild( "USMINI ${ it.mac }", "Device Type", "USMINI" )
                                        }
                                        ProcessData( "USMINI ${ it.mac }", it )
                                        break
                                    case "USF5P": // Unifi Switch Flex
                                        if( getChildDevice( "USF5P ${ it.mac }" ) == null ){
                                            PostEventToChild( "USF5P ${ it.mac }", "Device Type", "USF5P" )
                                        }
                                        ProcessData( "USF5P ${ it.mac }", it )
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
                case "CheckClient":
                    def ClientMAC = ""
                    def ConnectedMAC = ""
                    def TempMap = getChildDevice( "ClientCheck" ).ReturnState( "Client ${ data.ClientNumber }" )
                    if( TempMap == null ){
                        TempMap = [ MAC: "${ data.ClientMAC }", Status: "unknown", Name: "unknown", 'Last Seen': "unknown", 'Connected To MAC': "unknown", 'Connected to Name': "unknown", Speed: 0, 'Signal Strength': "unknown", SSID: "unknown" ]
                    }
                    Logging( "CheckClient Response = ${ Json }", 4 )
                    Json.data.each(){
                        if( it.mac != null ){
                            TempMap.Status = "Online"
                        } else {
                            TempMap.Status = "Offline"
                        }
                        if( it.name != null ){
                            TempMap.Name = it.name
                        } else if( it.hostname != null ){
                            TempMap.Name = it.hostname
                        } else if( TempMap.Name == null ){
                            TempMap.Name = "unknown"
                        }
                        if( it.last_seen != null ){
                            TempMap.'Last Seen' = ConvertEpochToDate( "${ it.last_seen }" )
                        }
                        if( it.is_wired ){
                            TempMap.'Connected To MAC' = it.sw_mac
                            TempMap.'Signal Strength' = "Wired"
                            TempMap.SSID = "NA"
                        } else {
                            TempMap.'Connected To MAC' = it.ap_mac
                            TempMap.'Signal Strength' = it.signal
                            TempMap.SSID = it.essid
                        }
                        getChildDevices().each{
                            if( it.deviceNetworkId.indexOf( TempMap.'Connected To MAC' ) != -1 ){
                                TempMap.'Connected To Name' = it.displayName
                            }
                        }
                        if( it.wired_rate_mbps != null ){
                            TempMap.Speed = it.wired_rate_mbps
                        }
                    }
                    PostEventToChild( "ClientCheck", "Client ${ data.ClientNumber }", TempMap )
                    break
                case "PresenceCheck":
                    ProcessEvent( "Last Presence Check", new Date() )
                    def ClientMAC = ""
                    def ConnectedMAC = ""
                    if( data.Manual ){
                        Json.data.each(){
                            if( it.mac != null ){
                                ClientMAC = it.mac
                                ProcessEvent( "Manual Presence Result", "${ ClientMAC } is present as of ${ ConvertEpochToDate( "${ it.last_seen }" ) }" )
                            }
							if( MACPresence != null ){
                                if( MACPresence.toLowerCase().indexOf( "${ ClientMAC }" ) >= 0 ){
                                    PostEventToChild( "Presence ${ ClientMAC }", "presence", "present" )
                                    if( getChildDevice( "Presence ${ ClientMAC }" ).label == null ){
                                        getChildDevice( "Presence ${ ClientMAC }" ).label = it.name
                                    }
                                    PostEventToChild( "Presence ${ ClientMAC }", "Last Seen", ConvertEpochToDate( "${ it.last_seen }" ) )
                                    if( it.is_wired ){
                                        ConnectedMAC = it.sw_mac
                                    } else {
                                        ConnectedMAC = it.ap_mac
                                    }
                                    PostEventToChild( "Presence ${ ClientMAC }", "Connected To MAC", ConnectedMAC )
                                    getChildDevices().each{
                                        if( it.deviceNetworkId.indexOf( "${ ConnectedMAC }" ) != -1 ){
                                            PostEventToChild( "Presence ${ ClientMAC }", "Connected To Name", it.displayName )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Json.data.each(){
                            ClientMAC = it.mac
                            if( MACPresence.toLowerCase().indexOf( "${ ClientMAC }" ) >= 0 ){
                                PostEventToChild( "Presence ${ ClientMAC }", "presence", "present" )
                                if( getChildDevice( "Presence ${ ClientMAC }" ).label == null ){
                                    getChildDevice( "Presence ${ ClientMAC }" ).label = it.name
                                }
                                PostEventToChild( "Presence ${ ClientMAC }", "Last Seen", ConvertEpochToDate( "${ it.last_seen }" ) )
                                if( it.is_wired ){
                                    ConnectedMAC = it.sw_mac
                                } else {
                                    ConnectedMAC = it.ap_mac
                                }
                                PostEventToChild( "Presence ${ ClientMAC }", "Connected To MAC", ConnectedMAC )
                                getChildDevices().each{
                                    if( it.deviceNetworkId.indexOf( "${ ConnectedMAC }" ) != -1 ){
                                        PostEventToChild( "Presence ${ ClientMAC }", "Connected To Name", it.displayName )
                                    }
                                }
                            }
                        }  
                    }
                    break
                case "MACExists":
                    Json.data.each(){
                        ProcessEvent( "MAC Exists Result", "${ it.mac } exists as of ${ ConvertEpochToDate( "${ it.last_seen }" ) }" )
                    }
                    break
                case "CurrentStats":
                    def TempOnline = 0
                    def TempUnifiDevices = 0
                    if( state.Site == null ){
                        ProcessEvent( "Site", "${ Json.data[ 0 ].name }" )
                    }
                    Json.data.each(){
                        def TempSite = "${ it.name }"
                        if( it.health != null ){
                            it.health.each(){
                                if( TempSite == state.Site ){
                                    ProcessEvent( "${ it.subsystem }-Health", "${ it.status }" )
                                }
                                if( it.num_adopted != null ){
                                    TempUnifiDevices = TempUnifiDevices + ( it.num_adopted ) as int
                                }
                                if( it.num_user != null ){
                                    TempOnline = TempOnline + ( it.num_user ) as int
                                }
                                ProcessState( "${ TempSite }-${ it.subsystem }-Health", "${ it.status }" )
                                if( it.subsystem != null ){
                                    switch( it.subsystem ){
                                        case "wan":
                                            if( it."gw_system-stats" != null ){
                                                if( it."gw_system-stats".cpu != null ){
                                                    ProcessEvent( "CPU", it."gw_system-stats".cpu, "%" )
                                                }
                                                if( it."gw_system-stats".mem != null ){
                                                    ProcessEvent( "Memory", it."gw_system-stats".mem, "%" )
                                                }
                                                if( it."gw_system-stats".uptime != null ){
                                                    def TempSeconds = it."gw_system-stats".uptime as int
                                                    def TempMinutes = ( TempSeconds / 60 ) as int
                                                    def TempHours = ( TempMinutes / 60 ) as int
                                                    def TempDays = ( TempHours / 24 ) as int
                                                    TempMinutes = ( TempMinutes % 60 )
                                                    TempHours = ( TempHours % 24 )
                                                    ProcessEvent( "Uptime", "${ TempDays } days ${ TempHours } hours ${ TempMinutes } minutes" )
                                                }
                                            }
                                            break
                                        case "wlan":
                                            break
                                        case "lan":
                                            break
                                        case "www":
                                            if( it.xput_up != null ){
                                                ProcessEvent( "Upload Speed", "${ it.xput_up }Mbps" )
                                            }
                                            if( it.xput_down != null ){
                                                ProcessEvent( "Download Speed", "${ it.xput_down }Mbps" )
                                            }
                                            if( it.latency != null ){
                                                ProcessEvent( "Latency", "${ it.latency }" )
                                            }
                                            break
                                        case "vpn":
                                            break
                                        default:
                                            Logging( "Unhandled SubSystem ${ it.subsystem } reported", 3 )
                                            break
                                    }
                                }
                            }
                        }
                    }
                    ProcessEvent( "Online Clients", TempOnline )
                    if( Controller == "Unifi Dream Machine (inc Pro)" ){
                        ProcessEvent( "Unifi Devices", ( TempUnifiDevices - 1 ) )
                    } else {
                        ProcessEvent( "Unifi Devices", TempUnifiDevices )
                    }
                    break
                case "CheckAlarms":
                    if( Json.data != null ){
                        def AlarmNumber = 1
                        def TempAlarms = []
                        Json.data.each{
                            if( it.msg != null ){
                                TempAlarms.push( [ "${ it.msg }" ] )
                                Logging( "Alarm: ${ it.msg }", 4 )
                            }
                            AlarmNumber++
                        }
                        ProcessEvent( "Alarms", TempAlarms )
                    } else {
                        ProcessEvent( "Alarms", "No active alarms" )
                    }
                    break
                case "PowerOutlet":
                    if( UnifiChildren ){
                        if( Json.data != null ){
                            if( Json.data[ 0 ] != null ){
                                if( Json.data[ 0 ].size() > 0 ){
                                    //ProcessData( "${ data.DNI }", Json.data[ 0 ] )
                                    if( Json.data[ 0 ].outlet_overrides != null ){
                                        if( Json.data[ 0 ].outlet_overrides.size() > 0 ){
                                            PostStateToChild( "${ data.DNI }", "Outlet Overrides",  Json.data[ 0 ].outlet_overrides )
                                            switch( getChildDevice( "${ data.DNI }" ).ReturnState( "Model" ) ){
												case "UP1":
													if( Json.data[ 0 ].outlet_overrides.relay_state ){
														PostEventToChild( "${ data.DNI }", "switch", "on" )
													} else {
														PostEventToChild( "${ data.DNI }", "switch", "off" )
													}
													break
												case "UP6":
													Json.data[ 0 ].outlet_overrides.each(){
														if( it.index != null && it.relay_state != null ){
															if( it.index != 7 ){
																if( it.relay_state ){
																	PostEventToChild( "${ data.DNI }", "Outlet ${ it.index }", "on" )
																} else {
																	PostEventToChild( "${ data.DNI }", "Outlet ${ it.index }", "off" )
																}
															} else {
																if( it.relay_state ){
																	PostEventToChild( "${ data.DNI }", "USB Ports", "on" )
																} else {
																	PostEventToChild( "${ data.DNI }", "USB Ports", "off" )
																}
															}
														}
													}
													break
												case "USPPDUP":
													Json.data[ 0 ].outlet_overrides.each(){
														if( it.index != null && it.relay_state != null ){
															if( it.index > 4 ){
																if( it.relay_state ){
																	PostEventToChild( "${ data.DNI }", "Outlet ${ it.index }", "on" )
																} else {
																	PostEventToChild( "${ data.DNI }", "Outlet ${ it.index }", "off" )
																}
															} else {
																if( it.relay_state ){
															        PostEventToChild( "${ data.DNI }", "USB Port ${ it.index }", "on" )
																} else {
																	PostEventToChild( "${ data.DNI }", "USB Port ${ it.index }", "off" )
																}
															}
														}
													}
													break
												default:
													Json.data[ 0 ].outlet_overrides.each(){
														if( it.index != null && it.relay_state != null ){
															if( it.relay_state ){
																PostEventToChild( "${ data.DNI }", "Outlet ${ it.index }", "on" )
															} else {
																PostEventToChild( "${ data.DNI }", "Outlet ${ it.index }", "off" )
															}
														}
													}
													break
											}
											break
                                        }
                                    }
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
                case "SetRPSPortOnOff":
                case "SetLEDOnOff":
                case "SetLEDColor":
                case "SetPortState":
                case "PowerCycleOutlet":
                case "PowerCyclePort":
                case "SetLEDBrightness":
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
                /*
                case "PresenceCheck":
                    ProcessEvent( "Last Presence Check", new Date() )
                    if( data.Manual ){
                        ProcessEvent( "Manual Presence Result", "${ data.MAC } is not present as of ${ new Date() }" )
                        if( MACPresence != null ){
                            if( MACPresence.toLowerCase().indexOf( data.MAC ) >= 0 ){
                                PostEventToChild( "Presence ${ data.MAC }", "presence", "not present" )
                            }
                        }
                    } else {
                        if( MACPresence.toLowerCase().indexOf( data.MAC ) >= 0 ){
                            PostEventToChild( "Presence ${ data.MAC }", "presence", "not present" )
                        }
                    }
                    break
                case "ClientCheck":
                    Logging( "CheckClient ${ data.ClientMAC } = Offline", 4 )
                    def ClientMAC = ""
                    def ConnectedMAC = ""
                    def TempMap = getChildDevice( "ClientCheck" ).ReturnState( "Client ${ data.ClientNumber }" )
                    if( TempMap == null ){
                        TempMap = [ MAC: "${ data.ClientMAC }", Status: "unknown", Name: "unknown", 'Last Seen': "unknown", 'Connected To MAC': "unknown", 'Connected to Name': "unknown", Speed: 0, 'Signal Strength': "unknown", SSID: "unknown" ]
                    }
                    TempMap.Status = "Offline"
                    TempMap.'Connected To MAC' = "None"
                    TempMap.'Signal Strength' = "unknown"
                    TempMap.SSID = "NA"
                    PostEventToChild( "ClientCheck", "Client ${ data.ClientNumber }", TempMap )
                    break
                case "MACExists":
                    Logging( "Bad Request = MAC ${ data.MAC } does not exist on controller.", 4 )
                    ProcessEvent( "MAC Exists Result", "${ data.MAC } does not exist as of ${ new Date() }" )
                    break
                */
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
            /*
            switch( data.Method ){
                case "PresenceCheck":
                    Logging( "Request Timeout checking if ${ data.MAC } is present", 5 )
                    break
                case "MACExists":
                    Logging( "Request Timeout checking if ${ data.MAC } exists", 5 )
                    break
                default:
                    Logging( "Request Timeout for ${ data.Method }", 5 )
			        break
            }
			break
            */
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

// Pings a specific IP address using the Hubitat not the Unifi
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
        log.info("Future update will add child items")
        /*
        if( getChildDevice( "${ Child }" ) == null ){
            TempChild = Child.split( " " )
            def ChildType = ""
            switch( TempChild[ 0 ] ){
                case "Presence":
                    ChildType = "Presence"
                    break
                case "UP6":
                    ChildType = "UP6"
                    break
                case "USPPDUP":
                    ChildType = "USPPDUP"
                    break
                case "USMINI":
                    ChildType = "USMINI"
                    break
                case "USF5P":
                    ChildType = "USF5P"
                    break
                case "USW48PoE":
                    ChildType = "USW48PoE"
                    break
                case "USW24PoE":
                    ChildType = "USW24PoE"
                    break
                case "USW16PoE":
                    ChildType = "USW16PoE"
                    break
                case "USW8PoE":
                    ChildType = "USW8PoE"
                    break
                case "USW16LPoE":
                    ChildType = "USW16LPoE"
                    break
                case "USW8PoE60":
                    ChildType = "USW8PoE60"
                    break
                case "USW8LPoE":
                    ChildType = "USW8LPoE"
                    break
                case "USW48":
                    ChildType = "USW48"
                    break
                case "USW24":
                    ChildType = "USW24"
                    break
                case "USW16":
                    ChildType = "USW16"
                    break
                case "USW8":
                    ChildType = "USW8"
                    break
                case "UHDIW":
                    ChildType = "UHDIW"
                    break
                case "BasicAP":
                    ChildType = "BasicAP"
                    break
                case "AP":
                    ChildType = "AP"
                    break
                case "RPS":
                    ChildType = "RPS"
                    break
                case "UDM":
                    ChildType = "UDM"
                    break
                case "UDMP":
                    ChildType = "UDMP"
                    break
                case "UDMPSE":
                    ChildType = "UDMPSE"
                    break
                case "UP1":
                case "Plug":
                    ChildType = "Plug"
                    break
                default:
                    ChildType = "Generic"
                    break
            }
            addChild( "${ Child }", ChildType )
        }
        if( getChildDevice( "${ Child }" ) != null ){
            if( Unit != null ){
                getChildDevice( "${ Child }" ).ProcessEvent( "${ Variable }", Value, "${ Unit }" )
                //Logging( "${ Child } Event: ${ Variable } = ${ Value }${ Unit }", 4 )
            } else {
                getChildDevice( "${ Child }" ).ProcessEvent( "${ Variable }", Value )
                //Logging( "${ Child } Event: ${ Variable } = ${ Value }", 4 )
            }
        } else {
            if( Unit != null ){
                Logging( "Failure to add ${ Child } and post ${ Variable }=${ Value }${ Unit }", 5 )
            } else {
                Logging( "Failure to add ${ Child } and post ${ Variable }=${ Value }", 5 )
            }
        }
        */
    } else {
        Logging( "Failure to add child because child name was null", 5 )
    }
}

// Post data to child device
def PostStateToChild( Child, Variable, Value ){
    if( "${ Child }" != null ){
       log.info("Future update will add child items")
       /*
        if( getChildDevice( "${ Child }" ) == null ){
            TempChild = Child.split( " " )
            def ChildType = ""
            switch( TempChild[ 0 ] ){
                case "Presence":
                    ChildType = "Presence"
                    break
                case "UP6":
                    ChildType = "UP6"
                    break
                case "USPPDUP":
                    ChildType = "USPPDUP"
                    break
                case "USMINI":
                    ChildType = "USMINI"
                    break
                case "USF5P":
                    ChildType = "USF5P"
                    break
                case "UHDIW":
                    ChildType = "UHDIW"
                    break
                case "USW48PoE":
                    ChildType = "USW48PoE"
                    break
                case "USW24PoE":
                    ChildType = "USW24PoE"
                    break
                case "USW16PoE":
                    ChildType = "USW16PoE"
                    break
                case "USW8PoE":
                    ChildType = "USW8PoE"
                    break
                case "USW16LPoE":
                    ChildType = "USW16LPoE"
                    break
                case "USW8PoE60":
                    ChildType = "USW8PoE60"
                    break
                case "USW8LPoE":
                    ChildType = "USW8LPoE"
                    break
                case "USW48":
                    ChildType = "USW48"
                    break
                case "USW24":
                    ChildType = "USW24"
                    break
                case "USW16":
                    ChildType = "USW16"
                    break
                case "USW8":
                    ChildType = "USW8"
                    break
                case "BasicAP":
                    ChildType = "BasicAP"
                    break
                case "AP":
                    ChildType = "AP"
                    break
                case "RPS":
                    ChildType = "RPS"
                    break
                case "UDM":
                    ChildType = "UDM"
                    break
                case "UDMP":
                    ChildType = "UDMP"
                    break
                case "UDMPSE":
                    ChildType = "UDMPSE"
                    break
                case "UP1":
                case "Plug":
                    ChildType = "Plug"
                    break
                default:
                    ChildType = "Generic"
                    break
            }
            addChild( "${ Child }", ChildType )
        }
        if( getChildDevice( "${ Child }" ) != null ){
            //Logging( "${ Child } State: ${ Variable } = ${ Value }", 4 )
            getChildDevice( "${ Child }" ).ProcessState( "${ Variable }", Value )
        } else {
            Logging( "Failure to add ${ Child } and post ${ Variable }=${ Value }", 5 )
        }
        */
    } else {
        Logging( "Failure to add child because child name was null", 5 )
    }
}

// Adds a Aqualind Children
// Based on @mircolino's method for child sensors
def addChild( String DNI, String ChildType ){
    try{
        Logging( "addChild(${ DNI })", 3 )
        log.info("Future update will add child items")
        /*
        if( UnifiChildren ){
            switch( ChildType ){
                case "Presence":
                    addChildDevice( "UnifiNetworkChild-Presence", DNI, [ name: "${ DNI }" ] )
                    break
                case "ClientCheck":
                    addChildDevice( "UnifiNetworkChild-ClientCheck", DNI, [ name: "${ DNI }" ] )
                    break
                case "UP6":
                    addChildDevice( "UnifiNetworkChild-UP6", DNI, [ name: "${ DNI }" ] )
                    break
                case "USPPDUP":
                    addChildDevice( "UnifiNetworkChild-USPPDUP", DNI, [ name: "${ DNI }" ] )
                    break
                case "USMINI":
                    addChildDevice( "UnifiNetworkChild-USMINI", DNI, [ name: "${ DNI }" ] )
                    break
                case "USF5P":
                    addChildDevice( "UnifiNetworkChild-USF5P", DNI, [ name: "${ DNI }" ] )
                    break
                case "UHDIW":
                    addChildDevice( "UnifiNetworkChild-UHDIW", DNI, [ name: "${ DNI }" ] )
                    break
                case "BasicAP":
                    addChildDevice( "UnifiNetworkChild-BasicAP", DNI, [ name: "${ DNI }" ] )
                    break
                case "AP":
                    addChildDevice( "UnifiNetworkChild-AP", DNI, [ name: "${ DNI }" ] )
                    break
                case "RPS":
                    addChildDevice( "UnifiNetworkChild-RPS", DNI, [ name: "${ DNI }" ] )
                    break
                case "UDM":
                    addChildDevice( "UnifiNetworkChild-UDM", DNI, [ name: "${ DNI }" ] )
                    break
                case "UDMP":
                    addChildDevice( "UnifiNetworkChild-UDMP", DNI, [ name: "${ DNI }" ] )
                    break
                case "UDMPSE":
                    addChildDevice( "UnifiNetworkChild-UDMPSE", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW48PoE":
                    addChildDevice( "UnifiNetworkChild-USW48PoE", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW48":
                    addChildDevice( "UnifiNetworkChild-USW48", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW24PoE":
                    addChildDevice( "UnifiNetworkChild-USW24PoE", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW24":
                    addChildDevice( "UnifiNetworkChild-USW24", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW16PoE":
                    addChildDevice( "UnifiNetworkChild-USW16PoE", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW16LPoE":
                    addChildDevice( "UnifiNetworkChild-USW16LPoE", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW16":
                    addChildDevice( "UnifiNetworkChild-USW16", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW8PoE":
                    addChildDevice( "UnifiNetworkChild-USW8PoE", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW8PoE60":
                    addChildDevice( "UnifiNetworkChild-USW8PoE60", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW8LPoE":
                    addChildDevice( "UnifiNetworkChild-USW8LPoE", DNI, [ name: "${ DNI }" ] )
                    break
                case "USW8":
                    addChildDevice( "UnifiNetworkChild-USW8", DNI, [ name: "${ DNI }" ] )
                    break
                case "UP1":
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