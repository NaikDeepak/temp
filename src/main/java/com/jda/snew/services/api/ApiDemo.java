package com.jda.snew.services.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("demo")
public class ApiDemo {

	@GET
	@Path("/version")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAppVersion() {
		return "v2.1";
	}

	@POST
	@Path("text")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String getText(String test) {
		return test;
	}

	@GET
	@Path("json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Truck> getJson() {
		List<Truck> trucks = new ArrayList<Truck>();
		trucks.add(new Truck("Volvo", 100000));
		trucks.add(new Truck("Tata", 200000));
		return trucks;
	}

	@GET
	@Path("xml")
	@Produces(MediaType.APPLICATION_XML)
	public String getXml() {
		return "<root><name>Volvo</name> <price>100000</price></root>";
	}

	// Use pathParams for mandatory params
	@GET
	@Path("pathParam/{param1}/{param2}")
	@Produces(MediaType.TEXT_PLAIN)
	public String pathParams(@PathParam("param1") String param1,
			@PathParam("param2") String param2) {
		return "Param1: " + param1 + "\nParam2: " + param2;
	}

	// Use queryParams for optional params
	@GET
	@Path("queryParam")
	@Produces(MediaType.TEXT_PLAIN)
	public String queryParam(@QueryParam("qparam1") String qparam1,
			@QueryParam("qparam2") String qparam2) {
		return "QParam1: " + qparam1 + "\nQParam2: " + qparam2;
	}

	@SuppressWarnings("unused")
	private static class Truck {
		private String name;
		private int cost;

		// dummy constructor for jackson
		public Truck() {
		}

		public Truck(String name, int cost) {
			super();
			this.name = name;
			this.cost = cost;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + cost;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Truck other = (Truck) obj;
			if (cost != other.cost)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
}