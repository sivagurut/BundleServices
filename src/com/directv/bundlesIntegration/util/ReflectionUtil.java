package com.directv.bundlesIntegration.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ReflectionUtil.
 */
public class ReflectionUtil {

	/**
	 * Gets the single instance of ReflectionUtil.
	 * 
	 * @return single instance of ReflectionUtil
	 */
	public static ReflectionUtil getInstance() {
		return ourInstance;
	}

	/**
	 * Instantiates a new reflection util.
	 */
	private ReflectionUtil() {
	}

	/**
	 * Copy.
	 * 
	 * @param orig the orig
	 * @param dest the dest
	 */
	public static void copy(Object orig, Object dest) {
		try {
			PropertyUtils.copyProperties(orig, dest);
		} catch (InvocationTargetException in) {
			in.printStackTrace();
		} catch (IllegalAccessException ill) {
			ill.printStackTrace();
		} catch (NoSuchMethodException nm) {
			nm.printStackTrace();
		}
	}

	/**
	 * Load class.
	 * 
	 * @param className the class name
	 * @return the object
	 */
	public static Object loadClass(String className) {
		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		} catch (InstantiationException inst) {
			inst.printStackTrace();
		} catch (IllegalAccessException ill) {
			ill.printStackTrace();
		}
		return obj;
	}

	/**
	 * Invoke method.
	 * 
	 * @param obj the obj
	 * @param methodName the method name
	 * @return the object
	 */
	public static Object invokeMethod(Object obj, String methodName) {
		Object result = null;
		try {
			String method = getMethodName(methodName, obj);
			if (method != null)
				result = MethodUtils.invokeMethod(obj, getMethodName(methodName, obj), null);
			else
				System.out.println((new StringBuilder()).append("PROPERTY NOT FOUND [ METHOD SEARCH: ").append(methodName)
						.append("   METHOD FOUND: ").append(method).append("    INSTANCE: ").append(obj.getClass()).append(" ]").toString());
		} catch (IllegalAccessException e) {
			System.out.println((new StringBuilder()).append("METHOD: ").append(methodName).toString());
			e.printStackTrace();
			System.out.println(e);
		} catch (InvocationTargetException e) {
			System.out.println((new StringBuilder()).append("METHOD: ").append(methodName).toString());
			e.printStackTrace();
			System.out.println(e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.out.println((new StringBuilder()).append("METHOD: ").append(methodName).toString());
			System.out.println(e);
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println((new StringBuilder()).append("METHOD: ").append(methodName).toString());
			System.out.println(n);
		}
		return result;
	}

	/**
	 * Invoke method.
	 * 
	 * @param obj the obj
	 * @param methodName the method name
	 * @param value the value
	 * @return the object
	 */
	public static Object invokeMethod(Object obj, String methodName, Object value) {
		Object result = null;
		try {
			String method = getMethodName(methodName, obj);
			if (method != null) {
				Object arguments[] = { value };
				Method methodFound = getMethod(methodName, obj.getClass());
				result = methodFound.invoke(obj, arguments);
			} else {
				System.out.println((new StringBuilder()).append("CAN NOT FIND THE FOLLOWING METHOD: ").append(method).append("  INSTANCE: ").append(
						obj).toString());
			}
		} catch (IllegalAccessException e) {
			System.out.println((new StringBuilder()).append("METHOD: ").append(methodName).toString());
			e.printStackTrace();
			System.out.println(e);
		} catch (InvocationTargetException e) {
			System.out.println((new StringBuilder()).append("METHOD: ").append(methodName).toString());
			e.printStackTrace();
			System.out.println(e);
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println((new StringBuilder()).append("METHOD: ").append(methodName).toString());
			System.out.println(n);
		}
		return result;
	}

	/**
	 * Creates the constructor.
	 * 
	 * @param comparatorDefinition the comparator definition
	 * @param intArgsClass the int args class
	 * @return the constructor
	 * @throws NoSuchMethodException the no such method exception
	 */
	@SuppressWarnings("unchecked")
	public static Constructor createConstructor(Class comparatorDefinition, Class intArgsClass[]) throws NoSuchMethodException {
		return comparatorDefinition.getConstructor(intArgsClass);
	}

	/**
	 * Creates the object.
	 * 
	 * @param constructor the constructor
	 * @param arguments the arguments
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	public static Object createObject(Constructor constructor, Object arguments[]) {
		Object object = null;
		try {
			object = constructor.newInstance(arguments);
			return object;
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return object;
	}

	/**
	 * Gets the method.
	 * 
	 * @param name the name
	 * @param kls the kls
	 * @return the method
	 */
	@SuppressWarnings("unchecked")
	public static Method getMethod(String name, Class kls) {
		Method methods[] = kls.getMethods();
		for (int i = 0; i < methods.length; i++)
			if (methods[i].getName().equalsIgnoreCase(name))
				return methods[i];

		return null;
	}

	/**
	 * Gets the method name.
	 * 
	 * @param name the name
	 * @param kls the kls
	 * @return the method name
	 */
	public static String getMethodName(String name, Object kls) {
		Method methods[] = kls.getClass().getMethods();
		for (int i = 0; i < methods.length; i++)
			if (methods[i].getName().equalsIgnoreCase(name.trim()))
				return methods[i].getName();

		return null;
	}

	/**
	 * Checks for interface.
	 * 
	 * @param obj the obj
	 * @param interfaceImplemented the interface implemented
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasInterface(Object obj, Class interfaceImplemented) {
		Class classes[] = obj.getClass().getInterfaces();
		for (int i = 0; i < classes.length; i++)
			if (classes[i].getName().equalsIgnoreCase(interfaceImplemented.getName()))
				return true;

		return false;
	}

	/**
	 * Prints the methods.
	 * 
	 * @param obj the obj
	 */
	public static void printMethods(Object obj) {
		Method m[] = obj.getClass().getMethods();
		for (int i = 0; i < m.length; i++)
			System.out.println((new StringBuilder()).append(i).append(" METHOD NAME: ").append(m[i].getName()).toString());

	}

	/**
	 * Describe.
	 * 
	 * @param obj the obj
	 * @return the map
	 */
	@SuppressWarnings("unchecked")
	public static Map describe(Object obj) {
		Map description = new HashMap();
		try {
			description.putAll(PropertyUtils.describe(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return description;
	}

	/**
	 * Gets the profile.
	 * 
	 * @param obj the obj
	 * @return the profile
	 */
	@SuppressWarnings("unchecked")
	public static Map getProfile(Object obj) {
		Map description = new TreeMap();
		try {
			Collection cls = PropertyUtils.describe(obj).keySet();
			Iterator iter = cls.iterator();
			do {
				if (!iter.hasNext())
					break;
				String name = (String) iter.next();
				Class klass = PropertyUtils.getPropertyType(obj, name);
				if (klass != null)
					description.put(name, klass.getName());
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return description;
	}

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String args[]) {
	}

	/** The our instance. */
	private static ReflectionUtil ourInstance = new ReflectionUtil();

}
