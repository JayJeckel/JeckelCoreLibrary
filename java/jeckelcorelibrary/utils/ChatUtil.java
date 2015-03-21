package jeckelcorelibrary.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * Static helper class to centralize chat related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class ChatUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private ChatUtil() { }

	/**
	 * Send chat message to the player.
	 * @param player Player to send the message to.
	 * @param text Message to send.
	 */
	public static void send(EntityPlayer player, String text) { player.addChatMessage(new ChatComponentText(text)); }

	/**
	 * Send chat message to the player using the given formatting.
	 * @param player Player to send the message to.
	 * @param text Message to send.
	 * @param format Chat format to apply to the message.
	 */
	public static void send(EntityPlayer player, String text, EnumChatFormatting format)
	{
		player.addChatMessage(new ChatComponentText(format + text));
	}

	/**
	 * Static class containing server related chat methods.
	 */
	public static final class Server
	{
		/**
		 * This is a "static" class and should not be instanced.
		 */
		private Server() { }

		/**
		 * Send chat message to the player if called on the server,
		 * otherwise do nothing.
		 * @param player Player to send the message to.
		 * @param text Message to send.
		 */
		public static void send(EntityPlayer player, String text)
		{
			if (!player.worldObj.isRemote) { player.addChatMessage(new ChatComponentText(text)); }
		}

		/**
		 * Send chat message to the player using the given formatting
		 * if called on the server, otherwise do nothing.
		 * @param player Player to send the message to.
		 * @param text Message to send.
		 * @param format Chat format to apply to the message.
		 */
		public static void send(EntityPlayer player, String text, EnumChatFormatting format)
		{
			if (!player.worldObj.isRemote) { player.addChatMessage(new ChatComponentText(format + text)); }
		}
	}

	/**
	 * Static class containing client related chat methods.
	 */
	public static final class Client
	{
		/**
		 * This is a "static" class and should not be instanced.
		 */
		private Client() { }

		/**
		 * Send chat message to the player if called on the client,
		 * otherwise do nothing.
		 * @param player Player to send the message to.
		 * @param text Message to send.
		 */
		public static void send(EntityPlayer player, String text)
		{
			if (player.worldObj.isRemote) { player.addChatMessage(new ChatComponentText(text)); }
		}

		/**
		 * Send chat message to the player using the given formatting
		 * if called on the client, otherwise do nothing.
		 * @param player Player to send the message to.
		 * @param text Message to send.
		 * @param format Chat format to apply to the message.
		 */
		public static void send(EntityPlayer player, String text, EnumChatFormatting format)
		{
			if (player.worldObj.isRemote) { player.addChatMessage(new ChatComponentText(format + text)); }
		}
	}
}
