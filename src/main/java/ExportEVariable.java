public class ExportEVariable {
    public static void setEnvironmentVariables(String env, String exportType) {    
       
        //condition for Export only NLPData
        if(exportType.equals("ExportNLP")){
            System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"allTasks\": true,\"customDashboards\": false,\"IncludeDependentTasks\": true}");
        }

        //condition for Export only Task and Sub Task
        else if(exportType.equals("ExportBotTasks")){
            System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": false,\"IncludeDependentTasks\": true}");
        }

        //condition for Export without Setting
        else if(exportType.equals("ExportWithOutSettings")){
            System.setProperty("Export_Body","{\"exportType\": \"published\",\"exportOptions\": {\"tasks\": [\"botTask\",\"knowledgeGraph\",\"smallTalk\"],\"nlpData\": [\"training_data\",\"bot_synonyms\",\"defaultDialog\",\"nlpSettings\",\"utterances\",\"patterns\",\"standardResponses\"]},\"subTasks\": {\"alerts\": [],\"actions\": [],\"dialogs\": []},\"allTasks\": true,\"customDashboards\": true,\"IncludeDependentTasks\": true}");
        }

        //condition for Export Full Bot
        else{
            System.setProperty("Export_Body","{\"exportType\": \"published\"}");
        }

        // for prod use workspace : VB and bot : demobot1
        if(env.equals("prod")){
            System.setProperty("Export_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWI2ZDY4Njk3LTA1ZmEtNTQwNC1iNzg4LTIxNWE3MWUwMjc0OSJ9.bRkzPwrHF2aWLhvS3e6iEI72XVsk6nuUVPWl-z0VaFQ");
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/st-fa3c2d6e-128d-5e18-a60a-eca34e4a9132/export/status");
            
        }
        // for qa use workspace : VB and bot : demobot2
        else if(env.equals("qa")){
            System.setProperty("Export_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLWJmNWFkNGUyLTE4MjgtNTc2MS04YmY2LTQyNjg2OGI0NWUyYiJ9.tl5Cy379FH0Tws4Mu2f9uDSnKoh0JIaxFgD5XjgQD3k");
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/st-c72008d0-ce30-571b-ba13-1078b89dbef4/export/status");
           
        }
        // for dev use diffrent account workspace : DB and bot : Exportbot
        else {
            System.setProperty("Export_JWT", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6ImNzLTUwZDA1N2YwLWY2MDUtNTljYS05OGFjLWI5MWJhMGQ3MmVlMiJ9.xmrKV3z7a9yoDlG7geOlCbaxgzWzVvvXM40LhdJya1A");
            System.setProperty("Export_URL", "https://bots.kore.ai/api/public/bot/st-e5669197-991d-5971-9417-a422368a0805/export");
            System.setProperty("ExportStatus_URL", "https://bots.kore.ai/api/public/bot/st-e5669197-991d-5971-9417-a422368a0805/export/status");
            
        }
            
        
        System.setProperty("ZipFile_Path", "fullexport.zip");
        System.setProperty("Dest_Dir", "ExportBot");
         
        
        
    }
}
