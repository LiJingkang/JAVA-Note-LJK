* 打印邻接表 // 每个节点保存的是父节点
    public void DisplayTree(int parentId,int level){
            String sql = "select NodeId,NodeName,ParentId from Tree where parentid="+ parentId;
            conn = sqlManager.GetConn();
            try {
                statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next()){
                    for(int i = 0;i < level*2;i++){
                        System.out.print("-");
                    }
                    System.out.println(rs.getString("NodeName"));
                    DisplayTree(rs.getInt("NodeId"),level+1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }