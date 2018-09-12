webpackJsonp([21],{SjFi:function(e,s,t){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var r={data:function(){return{tableData:[],database:null,totalAll:0,dialogFormVisible:!1,userAddressDisabled:!1,userListForm:{userAddress:"",userHelp:""},userListFormRules:{userAddress:[{required:!0,message:this.$t("message.c116"),trigger:"blur"},{min:10,max:35,message:this.$t("message.c117"),trigger:"blur"}]}}},components:{Back:t("LPk9").a},mounted:function(){var e=this;this.justifyIndexDEB(),setTimeout(function(){e.getUserList(e.database,1,15)},200)},methods:{addUserAccount:function(e){var s=this,t=this;this.$refs[e].validate(function(e){if(!e)return console.log("error submit!!"),!1;var r={userAddress:s.userListForm.userAddress,userHelp:s.userListForm.userHelp};t.editData(t.database,r)})},justifyIndexDEB:function(){"indexedDB"in window?this.createindexDB():console.log("对不起，您的浏览器不支持indexedDB，建议您使用google浏览器")},createindexDB:function(){var e=this,s="usersDB",t=1,r=window.indexedDB.open(s,t);r.onupgradeneeded=function(e){var s=e.target.result;s.objectStoreNames.contains("addressList")||s.createObjectStore("addressList",{keyPath:"userAddress",autoIncrement:!1})},r.onsuccess=function(s){e.database=s.target.result},r.onerror=function(e){console.log("数据库打开失败..."),console.dir(e)}},editData:function(e,s){var t=this,r=e.transaction(["addressList"],"readwrite").objectStore("addressList").put(s);r.onsuccess=function(e){t.getUserList(t.database,1,15),t.userListForm.userAddress="",t.userListForm.userHelp="",t.dialogFormVisible=!1,t.userAddressDisabled&&(t.userAddressDisabled=!1)},r.onerror=function(e){console.log("Error",e)}},delData:function(e,s){var t=this,r=e.transaction(["addressList"],"readwrite").objectStore("addressList").delete(s);r.onerror=function(e){console.log("删除数据失败！")},r.onsuccess=function(e){t.getUserList(t.database,1,15)}},getUserList:function(e,s,t){var r=this,i=[];e.transaction(["addressList"],"readonly").objectStore("addressList").openCursor().onsuccess=function(e){var s=e.target.result;s&&(i.push(s.value),s.continue())},setTimeout(function(){r.totalAll=i.length,r.tableData=1===s?i.slice(0,t):i.slice(15*(s-1),15*s)},50)},consensusSize:function(e){this.getUserList(e,15)},toNewAccount:function(){this.dialogFormVisible=!0,this.userAddressDisabled&&(this.userAddressDisabled=!1),this.userListForm.userAddress="",this.userListForm.userAlias="",this.userListForm.userHelp=""},editorRow:function(e,s,t){this.dialogFormVisible=!0,this.userAddressDisabled=!0,this.userListForm.userAddress=e,this.userListForm.userAlias=s,this.userListForm.userHelp=t},deleteRow:function(e){var s=this;this.$confirm(this.$t("message.c115"),this.$t("message.c86"),{confirmButtonText:this.$t("message.confirmButtonText"),cancelButtonText:this.$t("message.cancelButtonText")}).then(function(){s.delData(s.database,e)}).catch(function(){})},userListClose:function(){this.$refs.userListForm.resetFields()}}},i={render:function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",{staticClass:"users"},[t("Back",{attrs:{backTitle:this.$t("message.setManagement")}}),e._v(" "),t("div",{staticClass:"users-conter"},[t("h2",[e._v(e._s(e.$t("message.c93")))]),e._v(" "),t("el-button",{staticClass:"newAccount",attrs:{type:"primary",icon:"el-icon-plus"},on:{click:function(s){e.toNewAccount()}}}),e._v(" "),t("el-table",{attrs:{data:e.tableData}},[t("el-table-column",{attrs:{prop:"userAddress",label:e.$t("message.c69"),"min-width":"288",align:"center"}}),e._v(" "),t("el-table-column",{attrs:{prop:"userHelp",label:e.$t("message.remarks"),width:"180",align:"center"}}),e._v(" "),t("el-table-column",{attrs:{label:e.$t("message.operation"),width:"120",align:"center"},scopedSlots:e._u([{key:"default",fn:function(s){return[t("el-button",{attrs:{type:"text",size:"small"},on:{click:function(t){e.editorRow(s.row.userAddress,s.row.userAlias,s.row.userHelp)}}},[e._v("\n            "+e._s(e.$t("message.c94"))+"\n          ")]),e._v(" "),t("el-button",{attrs:{type:"text",size:"small"},on:{click:function(t){e.deleteRow(s.row.userAddress)}}},[e._v("\n            "+e._s(e.$t("message.c95"))+"\n          ")])]}}])})],1),e._v(" "),t("el-pagination",{directives:[{name:"show",rawName:"v-show",value:e.totalAllOk=this.totalAll>15,expression:"totalAllOk = this.totalAll>15 ?true:false"}],staticClass:"cb",attrs:{layout:"prev, pager, next","page-size":15,total:this.totalAll},on:{"current-change":e.consensusSize}})],1),e._v(" "),t("el-dialog",{attrs:{title:this.userAddressDisabled?e.$t("message.c961"):e.$t("message.c96"),visible:e.dialogFormVisible,top:"24vh"},on:{"update:visible":function(s){e.dialogFormVisible=s},close:e.userListClose}},[t("el-form",{ref:"userListForm",attrs:{model:e.userListForm,rules:e.userListFormRules,"label-width":"80px"}},[t("el-form-item",{attrs:{label:e.$t("message.c69"),prop:"userAddress"}},[t("el-input",{attrs:{maxlength:35,disabled:this.userAddressDisabled,onkeyup:"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')"},model:{value:e.userListForm.userAddress,callback:function(s){e.$set(e.userListForm,"userAddress","string"==typeof s?s.trim():s)},expression:"userListForm.userAddress"}})],1),e._v(" "),t("el-form-item",{attrs:{label:e.$t("message.remarks")}},[t("el-input",{attrs:{maxlength:20,onkeyup:"this.value=this.value.replace(/[^\\u4e00-\\u9fa5a-zA-Z0-9\\w]/g,'')"},model:{value:e.userListForm.userHelp,callback:function(s){e.$set(e.userListForm,"userHelp","string"==typeof s?s.trim():s)},expression:"userListForm.userHelp"}})],1)],1),e._v(" "),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{attrs:{type:"primary",id:"userList"},on:{click:function(s){e.addUserAccount("userListForm")}}},[e._v("\n        "+e._s(e.$t("message.confirmButtonText"))+"\n      ")])],1)],1)],1)},staticRenderFns:[]};var a=t("vSla")(r,i,!1,function(e){t("WQTz")},null,null);s.default=a.exports},WQTz:function(e,s){}});