package companyBots.Wizelinebot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You have a roadmap, which is the list of tasks that your team needs to
 * complete. Each task in this list has a title, a start date, an end date, and
 * a list of the people who will be working on it. You are given some queries,
 * each of which contains a specific person's name and a date. For each query
 * that is made, you need to return the list of tasks on which that person will
 * be working on the date specified in the query, sorted by the tasks' end
 * dates. If their end dates are equal, then sort by the tasks' titles.
 */
public class RoadMap {

	class Task implements Comparable<Task> {
		String name;
		Calendar start;
		Calendar end;
		Map<String, Boolean> usersMap;

		/**
		 * @param name
		 * @param start
		 * @param end
		 * @param usersMap
		 */
		public Task(String name, Calendar start, Calendar end, Map<String, Boolean> usersMap) {
			super();
			this.name = name;
			this.start = start;
			this.end = end;
			this.usersMap = usersMap;
		}

		public boolean isAssigned(Query query) {
			Boolean exists = usersMap.get(query.name);
			if (exists!=null && exists.booleanValue()) {
				return query.date.compareTo(start) >= 0 && query.date.compareTo(end) <= 0;
			}
			return false;
		}

		@Override
		public int compareTo(Task o) {
			int value = end.compareTo(o.end);
			if(value == 0) {
				return name.compareTo(o.name);
			}
			return value;
		}
	}

	class Query {
		String name;
		Calendar date;

		/**
		 * @param name
		 * @param date
		 */
		public Query(String name, Calendar date) {
			this.name = name;
			this.date = date;
		}
	}

	/**
	 * @param tasks
	 * @param queries
	 * @return
	 */
	String[][] roadmap(String[][] tasks, String[][] queries) {
		//
		String[][] results = new String[queries.length][];
		List<Task> taskList = new ArrayList<>();
		try {
			for (String[] task : tasks) {
				int length = task.length;
				String name = task[0];
				String startDateStr = task[1];
				Calendar startCal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				startCal.setTime(sdf.parse(startDateStr));

				String endDateStr = task[2];
				Calendar endCal = Calendar.getInstance();
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				endCal.setTime(sdf.parse(endDateStr));
				Map<String, Boolean> usersMap = new HashMap<>();
				for (int i = 3; i < length; i++) {
					usersMap.put(task[i], true);
				}
				Task t = new Task(name, startCal, endCal, usersMap);
				taskList.add(t);
			}
			//
			List<Query> queriesList = new ArrayList<>();
			int i = 0;
			for (String[] query : queries) {
				String name = query[0];
				String dateStr = query[1];
				Calendar date = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date.setTime(sdf.parse(dateStr));
				Query q = new Query(name, date);
				queriesList.add(q);
			}
			for (Query query : queriesList) {
				results[i] = find(query, taskList);
				i++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return results;
	}

	/**
	 * @param query
	 * @param taskList
	 * @return
	 */
	private String[] find(Query query, List<Task> taskList) {
		List<Task> tasksResults = new ArrayList<>();
		for (Task t : taskList) {
			if (t.isAssigned(query)) {
				tasksResults.add(t);
			}
		}
		//
		List<String> results = new ArrayList<String>();
		Collections.sort(tasksResults);
		for(Task t : tasksResults) {
			results.add(t.name);
		}
		return results.toArray(new String[0]);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RoadMap rm = new RoadMap();
		String[][] tasks = {{"YEEXQ","2017-01-07","2017-09-18","Landyn","Jamal","Corey","Reuben","Kyle","Daxton"}, 
		                      {"GURYR","2017-05-21","2017-08-09","Reuben","Jamal","Kyle","Corey","Landyn"}, 
							  {"RPHDE","2017-10-20","2017-12-10","Jenson","Vincenzo","Kyle"}, 
		                      {"QVCPD","2017-03-01","2017-09-23","Kaleb","Daxton","Vincenzo","Reuben","Corey","Kai"}, 
		                      {"XCDYD","2017-12-08","2017-12-20","Daxton","Kaleb","Reuben","Jenson","Kai","Corey"}};
		String[][] queries = {{"Jamal","2017-02-12"}, 
		                               {"Kyle","2017-01-09"}, 
		                               {"Landyn","2017-07-07"}, 
		                               {"Kai","2017-02-08"}, 
		                               {"Landyn","2017-06-25"}, 
		                               {"Daxton","2017-03-09"}, 
		                               {"Landyn","2017-10-25"}, 
		                               {"Daxton","2017-06-16"}, 
		                               {"Corey","2017-05-19"}, 
		                               {"Jamal","2017-09-24"}};
		

		String[][] results = rm.roadmap(tasks, queries);
		for(String[] result : results) {
			System.out.println(Arrays.toString(result));
		}
		
	}
}
