/*function showProjectList(){
	document.getElementById("include_header").style.display = 'block';
	document.getElementById("containers").style.display = 'block';
	document.getElementById("projectInfoHtml").style.display = 'none';
}*/
function HeaderController($scope,DataService,$filter) {
  console.log("载入HeaderController");
  $scope.headerItems = [{name:"用户管理",page:"userList"},{name:"项目管理",page:"projectList"},{name:"质量安全管理",page:"userList"},{name:"意见反馈",page:"userList"},{name:"关于我们",page:"userList"}];
 /* $scope.getUserList = function(data){
	  $scope.projectvisible = false;
	  $scope.uservisible = true;
  }
  $scope.getProjectList = function(data){
	  $scope.uservisible = false;
	  $scope.projectvisible = true;
  }*/
  
}